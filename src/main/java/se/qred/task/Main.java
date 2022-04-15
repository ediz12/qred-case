package se.qred.task;

import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import se.qred.task.client.AllabolagClient;
import se.qred.task.core.facade.ApplicationFacade;
import se.qred.task.core.facade.ContractFacade;
import se.qred.task.core.facade.OfferFacade;
import se.qred.task.core.mapper.request.ApplicationRequestMapper;
import se.qred.task.core.mapper.request.OfferRequestMapper;
import se.qred.task.core.mapper.request.OrganizationRequestMapper;
import se.qred.task.core.mapper.response.ApplicationResponseMapper;
import se.qred.task.core.mapper.request.ContractRequestMapper;
import se.qred.task.core.mapper.response.ContractResponseMapper;
import se.qred.task.core.mapper.response.OfferResponseMapper;
import se.qred.task.core.mapper.response.OrganizationResponseMapper;
import se.qred.task.core.service.checks.ExecutorHealthCheck;
import se.qred.task.db.ApplicationRepository;
import se.qred.task.db.ContractRepository;
import se.qred.task.db.OfferRepository;
import se.qred.task.db.OrganizationRepository;
import se.qred.task.db.dto.Contract;
import se.qred.task.db.dto.Offer;
import se.qred.task.db.dto.User;
import se.qred.task.db.dto.Application;
import se.qred.task.db.dto.Organization;
import se.qred.task.db.UserRepository;
import se.qred.task.core.service.ApplicationService;
import se.qred.task.core.service.ContractService;
import se.qred.task.core.service.OfferService;
import se.qred.task.core.service.OrganizationService;
import se.qred.task.core.service.SimpleAuthenticator;
import se.qred.task.core.util.CheckOfferExpirationDateTask;
import se.qred.task.resources.ApplicationResource;
import se.qred.task.resources.ContractResource;

import javax.ws.rs.client.Client;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Main extends io.dropwizard.Application<MainConfiguration> {

    public static void main(String[] args) throws Exception {
        new Main().run(args);
    }

    @Override
    public String getName() {
        return "main";
    }

    @Override
    public void initialize(Bootstrap<MainConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(MainConfiguration configuration,
                    Environment environment) {
        // TODO check dependency injection properly

        // Repositories
        final ApplicationRepository applicationRepository = new ApplicationRepository(hibernateBundle.getSessionFactory());
        final OrganizationRepository organizationRepository = new OrganizationRepository(hibernateBundle.getSessionFactory());
        final OfferRepository offerRepository = new OfferRepository(hibernateBundle.getSessionFactory());
        final ContractRepository contractRepository = new ContractRepository(hibernateBundle.getSessionFactory());
        final UserRepository userRepository = new UserRepository();
        userRepository.addDummyData();

        // Clients
        final Client client = new JerseyClientBuilder(environment).using(configuration.getJerseyClientConfiguration())
                .build(getName());
        AllabolagClient allabolagClient = new AllabolagClient(client);
        environment.jersey().register(allabolagClient);

        // Request Mappers
        ApplicationRequestMapper applicationRequestMapper = new ApplicationRequestMapper();
        OrganizationRequestMapper organizationRequestMapper = new OrganizationRequestMapper();
        OfferRequestMapper offerRequestMapper = new OfferRequestMapper();
        ContractRequestMapper contractRequestMapper = new ContractRequestMapper();

        // Response Mappers
        ApplicationResponseMapper applicationResponseMapper = new ApplicationResponseMapper();
        OrganizationResponseMapper organizationResponseMapper = new OrganizationResponseMapper();
        OfferResponseMapper offerResponseMapper = new OfferResponseMapper();
        ContractResponseMapper contractResponseMapper = new ContractResponseMapper();

        // Services
        ApplicationService applicationService = new ApplicationService(applicationRepository, applicationRequestMapper, applicationResponseMapper);
        OrganizationService organizationService = new OrganizationService(organizationRepository, organizationRequestMapper, organizationResponseMapper);
        OfferService offerService = new OfferService(offerRepository, offerRequestMapper, offerResponseMapper);
        ContractService contractService = new ContractService(contractRepository, contractRequestMapper, contractResponseMapper);

        // Facades
        final ApplicationFacade applicationFacade = new ApplicationFacade(applicationService, organizationService, offerService, allabolagClient);
        final OfferFacade offerFacade = new OfferFacade(applicationService, organizationService, offerService, contractService);
        final ContractFacade contractFacade = new ContractFacade(contractService, organizationService);

        // Resources
        final ApplicationResource applicationResource = new ApplicationResource(applicationFacade, offerFacade);
        environment.jersey().register(applicationResource);

        final ContractResource contractResource = new ContractResource(contractFacade);
        environment.jersey().register(contractResource);

        // Util
        CheckOfferExpirationDateTask task = new UnitOfWorkAwareProxyFactory(hibernateBundle)
                .create(CheckOfferExpirationDateTask.class, new Class[]{ApplicationService.class, OfferService.class}, new Object[]{applicationService, offerService});
        final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        // Health
        final ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(task, 15, 60, TimeUnit.SECONDS);
        environment.healthChecks().register("executor_health", new ExecutorHealthCheck(scheduledFuture));

        // Authentication
        // TODO move to database
        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(new SimpleAuthenticator(userRepository))
                        .setRealm("SUPER SECRET STUFF")
                        .buildAuthFilter()));
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    }

    HibernateBundle<MainConfiguration> hibernateBundle = new HibernateBundle<MainConfiguration>(Application.class, Organization.class, Offer.class, Contract.class) {
        @Override
        public PooledDataSourceFactory getDataSourceFactory(MainConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };
}

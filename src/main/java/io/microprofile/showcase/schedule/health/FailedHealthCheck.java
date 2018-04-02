package io.microprofile.showcase.schedule.health;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import io.microprofile.showcase.schedule.resources.ScheduleResource;

/**
 * 
 * @author jagraj
 *
 */
@Health
@ApplicationScoped
public class FailedHealthCheck implements HealthCheck{
	
	@Inject
	private ScheduleResource scheduleResource;
	@Inject 
	@ConfigProperty(name="isAppDown") Optional<String> isAppDown;
    private @Inject HealthCheckBean healthCheckBean;

    @Override
    public HealthCheckResponse call() {
		try {
			if(scheduleResource.nessProbe().getStatus()!=200 || ((isAppDown.isPresent()) && (isAppDown.get().equals("true")))) {
				return HealthCheckResponse.named("Schedule:failed-check").down().build();
			}
			else if(healthCheckBean.getIsAppDown()!=null && healthCheckBean.getIsAppDown().booleanValue()==true) {
				return HealthCheckResponse.named("Schedule:failed-check").down().build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return HealthCheckResponse.named("Schedule:successful-check").up().build();
    }
}

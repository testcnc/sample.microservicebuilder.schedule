/**
 * 
 */
package io.microprofile.showcase.schedule.health;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import io.microprofile.showcase.schedule.resources.ScheduleResource;

/**
 * @author jagraj
 *
 */
@Health
@ApplicationScoped
public class SuccessfulHealthCheck implements HealthCheck {
	
	@Inject
	private ScheduleResource scheduleResource;
	@Override
	public HealthCheckResponse call() {
		try {
			if(scheduleResource.nessProbe().getStatus()==200) {
				return HealthCheckResponse.named("Schedule:successful-check").up().build();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return HealthCheckResponse.named("Schedule:failed-check").down().build();
	}
}

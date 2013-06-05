package org.openmrs.module.reportingframeworktraining.reporting.cohort.definition;

import org.openmrs.module.reporting.cohort.definition.BaseCohortDefinition;
import org.openmrs.module.reporting.common.Localized;
import org.openmrs.module.reporting.definition.configuration.ConfigurationPropertyCachingStrategy;
import org.openmrs.module.reporting.evaluation.caching.Caching;

/**
 * Created with IntelliJ IDEA.
 * User: oliver
 * Date: 6/4/13
 * Time: 11:53 AM
 * To change this template use File | Settings | File Templates.
 */
@Caching(strategy=ConfigurationPropertyCachingStrategy.class)
@Localized("reporting.Females21To30AtMTRHCohortDefinition")
public class Females21To30AtMTRHCohortDefinition extends BaseCohortDefinition {
}

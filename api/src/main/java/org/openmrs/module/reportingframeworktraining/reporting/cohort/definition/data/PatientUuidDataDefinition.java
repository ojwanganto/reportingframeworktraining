package org.openmrs.module.reportingframeworktraining.reporting.cohort.definition.data;

import org.openmrs.module.reporting.data.BaseDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PersonDataDefinition;
import org.openmrs.module.reporting.definition.configuration.ConfigurationPropertyCachingStrategy;
import org.openmrs.module.reporting.evaluation.caching.Caching;

/**
 * Created with IntelliJ IDEA.
 * User: oliver
 * Date: 6/4/13
 * Time: 12:55 PM
 * To change this template use File | Settings | File Templates.
 */
@Caching(strategy=ConfigurationPropertyCachingStrategy.class)
public class PatientUuidDataDefinition extends BaseDataDefinition implements PersonDataDefinition {


    @Override
    public Class<?> getDataType() {
        return String.class;
    }
}

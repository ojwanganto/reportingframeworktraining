package org.openmrs.module.reportingframeworktraining.reporting.cohort.definition.data;

import org.openmrs.module.reporting.data.BaseDataDefinition;
import org.openmrs.module.reporting.data.person.definition.PersonDataDefinition;

/**
 * Created with IntelliJ IDEA.
 * User: oliver
 * Date: 6/10/13
 * Time: 8:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class LastVisitDateDataDefinition extends BaseDataDefinition implements PersonDataDefinition {

    @Override
    public Class<?> getDataType() {
        return String.class;
    }

}

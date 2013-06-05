package org.openmrs.module.reportingframeworktraining.reporting.cohort.definition.data.evaluator;

import org.openmrs.PersonAttributeType;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.data.patient.EvaluatedPatientData;
import org.openmrs.module.reporting.data.patient.definition.PatientDataDefinition;
import org.openmrs.module.reporting.data.patient.evaluator.PatientDataEvaluator;
import org.openmrs.module.reporting.dataset.query.service.DataSetQueryService;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: oliver
 * Date: 6/5/13
 * Time: 8:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class PatientPhoneContactDataDefinitionEvaluator implements PatientDataEvaluator {

    public static final String CONTACT_PHONE_ATTRIBUTE_TYPE = "Contact Phone Number";

    public EvaluatedPatientData evaluate(PatientDataDefinition definition, EvaluationContext context) throws EvaluationException {

        EvaluatedPatientData c = new EvaluatedPatientData(definition, context);

        PersonAttributeType pat = Context.getPersonService().getPersonAttributeTypeByName(CONTACT_PHONE_ATTRIBUTE_TYPE);
        Integer patID = pat.getId();


        if (context.getBaseCohort() != null && context.getBaseCohort().isEmpty()) {
            return c;
        }

        DataSetQueryService qs = Context.getService(DataSetQueryService.class);

        StringBuilder hql = new StringBuilder();
        Map<String, Object> m = new HashMap<String, Object>();

        hql.append("select value from PersonAttribute   ");
        hql.append("where voided = false and PersonAttribute.person_id in  (:patientIds) ");
        hql.append(" and PersonAttribute.person_attribute_type_id in (:patID) ");
        m.put("patientIds", context.getBaseCohort());


        List<Object> queryResult = qs.executeHqlQuery(hql.toString(), m);

        String phoneNumber = queryResult.get(0).toString();

        if (context.getBaseCohort() != null) {
            for (Integer pId : context.getBaseCohort().getMemberIds()) {
                c.addData(pId, phoneNumber);
            }
        }

        return c;
    }
}

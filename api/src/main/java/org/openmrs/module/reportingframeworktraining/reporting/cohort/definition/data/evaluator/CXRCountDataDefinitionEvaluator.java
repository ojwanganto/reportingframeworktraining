package org.openmrs.module.reportingframeworktraining.reporting.cohort.definition.data.evaluator;

import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.data.patient.EvaluatedPatientData;
import org.openmrs.module.reporting.data.patient.definition.PatientDataDefinition;
import org.openmrs.module.reporting.data.patient.evaluator.PatientDataEvaluator;
import org.openmrs.module.reporting.dataset.query.service.DataSetQueryService;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reportingframeworktraining.reporting.cohort.definition.data.CXRCountDataDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: oliver
 * Date: 6/10/13
 * Time: 11:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class CXRCountDataDefinitionEvaluator implements PatientDataEvaluator {

    @Override
    public EvaluatedPatientData evaluate(PatientDataDefinition definition, EvaluationContext context) throws EvaluationException {

        CXRCountDataDefinition def = (CXRCountDataDefinition)definition;

        EvaluatedPatientData c = new EvaluatedPatientData(definition, context);

        if (context.getBaseCohort() != null && context.getBaseCohort().isEmpty()) {
            return c;
        }

        DataSetQueryService qs = Context.getService(DataSetQueryService.class);

        StringBuilder hql = new StringBuilder();
        Map<String, Object> m = new HashMap<String, Object>();

        hql.append("from 		Obs ");
        hql.append("where 		voided = false ");

        if (context.getBaseCohort() != null) {
            hql.append("and 		patientId in (:patientIds) ");
            m.put("patientIds", context.getBaseCohort());
        }
        hql.append(" and concept in (7859) ");


        List<Object> queryResult = qs.executeHqlQuery(hql.toString(), m);

        Integer cXRCount = queryResult.size();

        if (context.getBaseCohort() != null) {
            for (Integer pId : context.getBaseCohort().getMemberIds()) {

                if(cXRCount>0){
                    c.addData(pId, cXRCount);
                }
                else{
                    c.addData(pId, "No CXR Count");
                }

            }
        }

        return c;
    }
}


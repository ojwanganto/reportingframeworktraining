package org.openmrs.module.reportingframeworktraining.reporting.cohort.definition.data.evaluator;

import org.openmrs.Encounter;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.data.patient.EvaluatedPatientData;
import org.openmrs.module.reporting.data.patient.definition.PatientDataDefinition;
import org.openmrs.module.reporting.data.patient.evaluator.PatientDataEvaluator;
import org.openmrs.module.reporting.dataset.query.service.DataSetQueryService;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reportingframeworktraining.reporting.cohort.definition.data.FirstEncounterDateTimeDataDefinition;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: oliver
 * Date: 6/10/13
 * Time: 11:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class FirstEncounterDateTimeDataDefinitionEvaluator implements PatientDataEvaluator {

    @Override
    public EvaluatedPatientData evaluate(PatientDataDefinition definition, EvaluationContext context) throws EvaluationException {
        FirstEncounterDateTimeDataDefinition def = (FirstEncounterDateTimeDataDefinition)definition;
        EvaluatedPatientData c = new EvaluatedPatientData((PatientDataDefinition)def, context);

        if (context.getBaseCohort() != null && context.getBaseCohort().isEmpty()) {
            return c;
        }

        DataSetQueryService qs = Context.getService(DataSetQueryService.class);

        StringBuilder hql = new StringBuilder();
        Map<String, Object> m = new HashMap<String, Object>();

        hql.append("from 		Encounter ");
        hql.append("where 		voided = false ");

        if (context.getBaseCohort() != null) {
            hql.append("and 		patientId in (:patientIds) ");
            m.put("patientIds", context.getBaseCohort());
        }

        hql.append("order by 	encounterDatetime asc limit 1 ");

        List<Object> queryResult = qs.executeHqlQuery(hql.toString(), m);

        Encounter lastEncounter = (Encounter)queryResult.get(0);

        Date firstEncounterEncounterDatetime = lastEncounter.getEncounterDatetime();

        if (context.getBaseCohort() != null) {
            for (Integer pId : context.getBaseCohort().getMemberIds()) {
                c.addData(pId, firstEncounterEncounterDatetime);
            }
        }

        return c;
    }
}


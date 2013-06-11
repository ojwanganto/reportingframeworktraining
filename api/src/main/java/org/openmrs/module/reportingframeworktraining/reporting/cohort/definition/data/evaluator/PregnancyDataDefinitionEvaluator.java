package org.openmrs.module.reportingframeworktraining.reporting.cohort.definition.data.evaluator;

import org.openmrs.Obs;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.common.ListMap;
import org.openmrs.module.reporting.data.patient.EvaluatedPatientData;
import org.openmrs.module.reporting.data.patient.definition.PatientDataDefinition;
import org.openmrs.module.reporting.data.patient.evaluator.PatientDataEvaluator;
import org.openmrs.module.reporting.dataset.query.service.DataSetQueryService;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reportingframeworktraining.reporting.cohort.definition.data.PregnancyDataDefinition;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: oliver
 * Date: 6/10/13
 * Time: 11:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class PregnancyDataDefinitionEvaluator implements PatientDataEvaluator {

    @Override
    public EvaluatedPatientData evaluate(PatientDataDefinition definition, EvaluationContext context) throws EvaluationException {

        PregnancyDataDefinition def = (PregnancyDataDefinition)definition;

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
        hql.append(" and concept in (5596, 6743) ");


        ListMap<Integer, Date> dateMap = makeValueDatesMapFromSQL(hql.toString(), m);

        for (Integer memberId : dateMap.keySet()) {
            List<String> EDDs = new ArrayList<String>();
            for (Date d : safeFind(dateMap, memberId)) {
                EDDs.add(d.toString());
            }
            c.addData(memberId, EDDs);
        }

        return c;
    }

    private Set<Date> safeFind(final ListMap<Integer, Date> map, final Integer key) {
        Set<Date> dateSet = new LinkedHashSet<Date>();
        if (map.containsKey(key))
            dateSet.addAll(map.get(key));
        return dateSet;
    }

    /**
     * replaces reportDate and personIds with data from private variables before generating a date map
     */
    private ListMap<Integer, Date> makeValueDatesMapFromSQL(final String query, final Map<String, Object> substitutions) {
        DataSetQueryService qs = Context.getService(DataSetQueryService.class);
        List<Object> queryResult = qs.executeHqlQuery(query, substitutions);

        ListMap<Integer, Date> dateListMap = new ListMap<Integer, Date>();
        for (Object o : queryResult) {
            Obs obs = (Obs) o;
            dateListMap.put(obs.getPersonId(), obs.getValueDatetime());
            //dateListMap.putInList(obs.getPersonId(), obs.getValueDatetime());
        }

        return dateListMap;
    }
}


package org.joo.libra.test;

import org.joo.libra.PredicateContext;
import org.joo.libra.sql.SqlPredicate;
import org.joo.libra.support.ObjectUtils;
import org.joo.libra.support.eval.VariableEvaluator;
import org.joo.libra.support.eval.impl.CompiledJavaEvaluator;
import org.joo.libra.test.support.AnotherPerson;
import org.joo.libra.test.support.JobWithSalary;
import org.joo.libra.test.support.MockDataUtils;
import org.joo.libra.test.support.Person;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TestCompile {

    @Test
    public void testDate() throws Exception {
        Date date = new Date(2024,4,1);
        Date date1 = new Date(2024,4,7);

        Person p = MockDataUtils.mockPerson();
        p.setData("a");
        p.setName("hello");
        p.setBirthDay(date);
        p.setBirthDay1(date1);

        Map<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("a","123");
        stringObjectHashMap.put("b","456");
        p.setMapTest(stringObjectHashMap);


        JobWithSalary oracle = new JobWithSalary("Oracle", 1000);
        JobWithSalary java = new JobWithSalary("java", 1020);
        p.setJobWithSalaries(Arrays.asList(oracle,java));
//        VariableEvaluator evaluator = new CompiledJavaEvaluator();
//        Assert.assertEquals(date, evaluator.evaluate(p, "birthDay"));
//{'abc', 2, 3} contains 'abc'
        //ANY $item IN items SATISFIES $item.qty > 1000
        PredicateContext context = new PredicateContext(p);
//        SqlPredicate predicate = new SqlPredicate("{'hello1', 2, 3} contains 'hello1' and {'hello1', 2, 3} contains 2");
//        SqlPredicate predicate = new SqlPredicate("sum($job.salary with $job in jobWithSalaries satisfies $job.salary > 500) == 1000");
//        SqlPredicate predicate = new SqlPredicate("sum($job.salary for $job in jobWithSalaries if $job.salary > 500)==2020");
        context.setTempVariable("$jobWithSalaries",Arrays.asList(oracle,java));
//        SqlPredicate predicate = new SqlPredicate("all for $job in jobWithSalaries if $job.salary > 10000");
//        SqlPredicate predicate = new SqlPredicate("join($jobWithSalaries,'name')");

//        SqlPredicate predicate = new SqlPredicate("diffDays(birthDay,birthDay1)");

//        SqlPredicate predicate = new SqlPredicate("jobs append '123'");

//        SqlPredicate predicate = new SqlPredicate("print (mapTest[data])[1:2]");
        SqlPredicate predicate = new SqlPredicate("print mapTest[data]");


        if (predicate.hasError()) {
            predicate.checkForErrorAndThrow();
        }

//        boolean b = predicate.test(context);
        Object value = predicate.calculateLiteralValue(context);
//        System.out.println(b);
        System.out.println(value);
    }

    @Test
    public void testCompile() throws Exception {
        Person p = MockDataUtils.mockPerson();
        VariableEvaluator evaluator = new CompiledJavaEvaluator();
        Assert.assertEquals(27L, evaluator.evaluate(p, "age"));
        Assert.assertEquals("John", evaluator.evaluate(p, "name"));
        Assert.assertEquals("male", evaluator.evaluate(p, "demographic.gender"));
        Assert.assertEquals("Oracle", evaluator.evaluate(p, "jobs[0]"));
    }

    @Test
    public void testCompileWithTwoClasses() throws Exception {
        Person p = MockDataUtils.mockPerson();
        AnotherPerson p2 = new AnotherPerson();
        p2.setAge("123");
        VariableEvaluator evaluator = new CompiledJavaEvaluator();
        Assert.assertEquals(27L, evaluator.evaluate(p, "age"));
        Assert.assertEquals("123", evaluator.evaluate(p2, "age"));
    }

    @Test
    public void testPerfCompiled() throws Exception {
        Person p = MockDataUtils.mockPerson();
        VariableEvaluator evaluator = new CompiledJavaEvaluator();
        Assert.assertEquals("male", evaluator.evaluate(p, "demographic.gender"));

        int total = 1000000;
        long started = System.nanoTime();
        for (int i = 0; i < total; i++) {
            evaluator.evaluate(p, "demographic.gender");
        }
        long elapsed = System.nanoTime() - started;
        long throughput = (long) ((double) total / elapsed * 1e9);
        System.out.println("Elapsed: " + (long) (elapsed / 1e6) + "ms. Throughput: " + throughput);
    }

    @Test
    public void testPerfBean() throws Exception {
        Person p = MockDataUtils.mockPerson();

        int total = 1000000;
        long started = System.nanoTime();
        for (int i = 0; i < total; i++) {
            ObjectUtils.getValue(p, "demographic.gender");
        }
        long elapsed = System.nanoTime() - started;
        long throughput = (long) ((double) total / elapsed * 1e9);
        System.out.println("Elapsed: " + (long) (elapsed / 1e6) + "ms. Throughput: " + throughput);
    }
}

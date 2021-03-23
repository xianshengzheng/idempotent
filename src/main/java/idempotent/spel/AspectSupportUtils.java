package idempotent.spel;


import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;


/**
 * @author zhenghao
 */
public class AspectSupportUtils {
    private static LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    public static String parseExpression(String idExpression, Method method, Object[] args) {
        if (idExpression == null || idExpression.equals("")){
            throw new IllegalArgumentException("id不能为空");
        }

        String[] paramNameArr = discoverer.getParameterNames(method);
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < paramNameArr.length; i++) {
            context.setVariable(paramNameArr[i], args[i]);
        }
        String result = parser.parseExpression(idExpression).getValue(context, String.class);
        return result;
    }

}

package org.shield.fadada.sdk.processor;

import static feign.Util.checkState;
import static feign.Util.emptyToNull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import org.shield.fadada.sdk.annatation.AffixParam;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import feign.MethodMetadata;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
public class AffixProcessor implements AnnotatedParameterProcessor {

    private static final Class<AffixParam> ANNOTATION = AffixParam.class;

    @Override
    public Class<? extends Annotation> getAnnotationType() {
        return ANNOTATION;
    }

    @Override
    public boolean processArgument(AnnotatedParameterContext context, Annotation annotation, Method method) {
        int parameterIndex = context.getParameterIndex();
        Class<?> parameterType = method.getParameterTypes()[parameterIndex];
        MethodMetadata data = context.getMethodMetadata();

        if (Map.class.isAssignableFrom(parameterType)) {
            checkState(data.queryMapIndex() == null, "Query map can only be present once.");
            data.queryMapIndex(parameterIndex);

            return true;
        }

        AffixParam param = ANNOTATION.cast(annotation);
        String name = param.value();
        checkState(emptyToNull(name) != null, "AffixParam.value() was empty on parameter %s", parameterIndex);
        context.setParameterName(name);

        Collection<String> query = context.setTemplateParameter(name, data.template().queries().get(name));
        if (!data.template().queries().containsKey(name)) {
            data.template().query(name, query);
        }
        // 将需要签名的参数放入隐藏头部中
        Integer order = param.order().isEmpty() ? parameterIndex : Integer.valueOf(param.order());
        data.template().header(String.format("%s%03d_%s", AffixParam.HEADER_PREFIX, order, name),
                query.iterator().next());
        return true;
    }
}

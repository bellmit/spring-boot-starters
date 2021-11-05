package org.shield.fadada.sdk.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import org.shield.fadada.sdk.annatation.Sha1Param;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import feign.MethodMetadata;

import static feign.Util.emptyToNull;
import static feign.Util.checkState;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
public class Sha1Processor implements AnnotatedParameterProcessor {

    private static final Class<Sha1Param> ANNOTATION = Sha1Param.class;

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

        Sha1Param param = ANNOTATION.cast(annotation);
        String name = param.value();
        checkState(emptyToNull(name) != null, "Sha1Param.value() was empty on parameter %s", parameterIndex);
        context.setParameterName(name);

        Collection<String> query = context.setTemplateParameter(name, data.template().queries().get(name));
        if (!data.template().queries().containsKey(name)) {
            data.template().query(name, query);
        }
        // 将需要签名的参数放入隐藏头部中
        Integer order = param.order().isEmpty() ? 0 : Integer.valueOf(param.order());
        data.template().header(String.format("%s%03d_%s", Sha1Param.HEADER_PREFIX, order, name),
                query.iterator().next());
        return true;
    }
}

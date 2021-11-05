package org.shield.fadada.sdk.processor;

import static feign.Util.checkState;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import org.shield.fadada.sdk.annatation.AffixParam;
import org.shield.fadada.sdk.annatation.HasAffixParam;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import feign.MethodMetadata;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
public class HasAffixParamProcessor implements AnnotatedParameterProcessor {

    private static final Class<HasAffixParam> ANNOTATION = HasAffixParam.class;

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

        Parameter parameter = method.getParameters()[parameterIndex];
        Class<?> type = parameter.getType();

        Field[] fields = type.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            if (!field.isAnnotationPresent(AffixParam.class)) {
                continue;
            }
            try {
                AffixParam anno = field.getAnnotation(AffixParam.class);
                String fieldName = anno.value().isEmpty() ? field.getName() : anno.value();
                // 将需要签名的参数放入隐藏头部中
                Integer order = anno.order().isEmpty() ? 0 : Integer.parseInt(anno.order());
                data.template().header(String.format("%s%03d_%s", AffixParam.HEADER_PREFIX, order, fieldName),
                HasAffixParam.DEFAULT_VALUE);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}

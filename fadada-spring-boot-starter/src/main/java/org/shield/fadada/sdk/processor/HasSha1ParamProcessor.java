package org.shield.fadada.sdk.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

import org.shield.fadada.sdk.annatation.HasSha1Param;
import org.shield.fadada.sdk.annatation.Sha1Param;
import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import feign.MethodMetadata;

import static feign.Util.checkState;

/**
 * @author zacksleo <zacksleo@gmail.com>
 */
public class HasSha1ParamProcessor implements AnnotatedParameterProcessor {

    private static final Class<HasSha1Param> ANNOTATION = HasSha1Param.class;

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
            if (!field.isAnnotationPresent(Sha1Param.class)) {
                continue;
            }
            try {
                Sha1Param anno = field.getAnnotation(Sha1Param.class);
                String fieldName = anno.value().isEmpty() ? field.getName() : anno.value();
                // 将需要签名的参数放入隐藏头部中
                Integer order = anno.order().isEmpty() ? 0 : Integer.parseInt(anno.order());
                data.template().header(String.format("%s%03d_%s", Sha1Param.HEADER_PREFIX, order, fieldName),
                        HasSha1Param.DEFAULT_VALUE);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
}

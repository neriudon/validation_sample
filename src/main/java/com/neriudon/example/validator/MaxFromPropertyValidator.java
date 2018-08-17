package com.neriudon.example.validator;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.beans.factory.config.PropertyPathFactoryBean;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertyResolver;
import org.springframework.core.env.PropertySourcesPropertyResolver;

/**
 * 数値をチェックするバリデータ.<br>
 */
public class MaxFromPropertyValidator extends ApplicationObjectSupport
		implements ConstraintValidator<MaxFromProperty, Long> {

	/** 許容する最大値. */
	private long max;

	/** リゾルバ. */
	private final PropertyResolver resolver;

	private Properties properties;

	public MaxFromPropertyValidator(List<PropertySourcesPlaceholderConfigurer> configurers, Environment environment) {

//		try {
//			properties = getApplicationContext().getBean(PropertiesFactoryBean.class).getObject();
//		} catch (BeansException | IllegalStateException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		if (configurers != null) {
			MutablePropertySources propertySources = new MutablePropertySources();
			configurers.forEach(c -> c.getAppliedPropertySources().forEach(p -> {
				CompositePropertySource temp = new CompositePropertySource(
						c.toString().concat("$").concat(p.getName()));
				temp.addPropertySource(p);
				propertySources.addLast(temp);
			}));
			this.resolver = new PropertySourcesPropertyResolver(propertySources);
		} else {
			this.resolver = environment;
		}
	}

	/**
	 * 初期化処理.<br>
	 * パラメータの取得と検証をおこなう
	 *
	 * @param constraintAnnotation
	 *            {@linkplain MaxConfiguredByProperty}
	 */
	@Override
	public void initialize(MaxFromProperty constraintAnnotation) {
		max = getSizeValue(constraintAnnotation.value());
		validateSizeParameters();
	}

	/**
	 * 対象の数値が指定された値以下か検証する.
	 *
	 * @param value
	 *            検証する数値
	 * @param context
	 *            コンテキスト
	 * @return 対象の数値が指定された値以下かnullの場合はtrueを返却する
	 */
	@Override
	public boolean isValid(Long value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		return value <= max;
	}

	/**
	 * プロパティキーに紐づく数値を返す. <br>
	 * 紐づく値がない場合は、キーを数値にして返す
	 *
	 * @param key
	 *            プロパティキーまたは数値
	 * @return 数値
	 */
	private int getSizeValue(String key) {
		String value = resolver.getProperty(key, key);
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(
					"failed to get int value from Property(key:" + key + ", value:" + value + ")");
		}
	}

	/**
	 * 最大値の設定値の検証を行う.<br>
	 * 以下の場合は例外をスローする
	 * <ul>
	 * <li>{@code max}が0未満の時</li>
	 * </ul>
	 * 検証でエラーが発生した場合、例外をスローする.
	 */
	private void validateSizeParameters() {
		if (this.max < 0) {
			throw new IllegalArgumentException("max must be greater than or equal to 0");
		}
	}
}

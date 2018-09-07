# validation_sample
samples of bean validation.

1. @CodeExists
Checking the field value or method's return value is defined in const class or not.

1. @AcceptedXxxValues
Checking the field value or method's return value is contained annotation value or not.

1. @MaxFromProperty
Extended @Max annotation which can be set value refered property file.

More details, please refer test class.


```java
	private static class StringCodeSample {
		@CodeExists(StringCode.class)
		private String code;

		public StringCodeSample(String code) {
			this.code = code;
		}
	}

	private static class AcceptedStringValuesSample {

		@AcceptedStringValues({ "1", "2", "3", "4", "5" })
		private String code;

		public AcceptedStringValuesSample(String code) {
			this.code = code;
		}
	}
  
 	private static class MaxFromPropertySample {

		@MaxFromProperty("max")
		private long value;

		public MaxFromPropertySample(long value) {
			this.value = value;
		}
	}
```

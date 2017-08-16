
/* Kotlin Annotation 은 자바와 호환되며 Java Annotation 도 Kotlin 과 호환된다 */
public class AnnotationTestJava {
    @AnnotationWithParam(why = "java example") public void print() {

    }

    public @interface JavaAnn {
        int intValue();
        String stringValue();
    }

    public @interface JavaAnnWithValue {
        String value();
    }

    public @interface JavaAnnWithArrayValue {
        String[] value();
    }


}


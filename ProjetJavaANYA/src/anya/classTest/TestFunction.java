package anya.classTest;

public class TestFunction {

    public static boolean isNonNull(Object a, String pos) {
        if (a == null) {
            System.out.println("[FAIL] L'objet " + pos + " est nul");
            return false;
        }
        return true;
    }
    
    public static boolean isNull(Object a, String operationName) {
        if (a == null) {
            System.out.println("[SUCS] L'opération " + operationName + " a réussi par égalité à null");
            return true;
        }
        System.out.println("[FAIL] L'opération " + operationName + " a échoué par inégalité à null");
        return false;
    }

    public static boolean areEqual(Object a, Object b, String operationName) {
        if (!(isNonNull(a, "a") & isNonNull(b, "b"))) {
            return false;
        }

        if (a == b) {
            System.out.println("[SUCS] L'opération " + operationName + " a réussi par égalité");
            return true;
        }

        if (a.equals(b)) {
            System.out.println("[SUCS] L'opération " + operationName + " a réussi par égalité");
            return true;
        } else {
            System.out.println("[FAIL] L'opération " + operationName + " a échoué");
            return false;
        }
    }

    public static boolean isType(Object a, Class<?> expectedType, String operationName) {
        if (!isNonNull(a, "a")) {
            return false;
        }

        if (a.getClass() == expectedType) {
            System.out.println("[SUCS] L'opération " + operationName + " a réussi par conformité au type attendu");
            return true;
        } else {
            System.out.println("[FAIL] L'opération " + operationName + " a échoué");
            return false;
        }
    }
}

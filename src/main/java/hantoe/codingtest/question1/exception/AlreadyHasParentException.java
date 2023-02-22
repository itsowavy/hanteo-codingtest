package hantoe.codingtest.question1.exception;

/**
 * 상위 카테고리를 중복으로 허용하지 않는 카테고리가 두 개의 상위 카테고리의 하위 카테고리로 설정되면 해당 Exception이 발생합니다.
 */
public class AlreadyHasParentException extends RuntimeException{

    public AlreadyHasParentException() {
        super("상위 카테고리가 이미 존재합니다.");
    }
}

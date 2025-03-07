package day1_10_07.variable;

public class Variable1 {
    //여기(클래스 선언 좌중괄호와 우중괄호 안에)에 선언하면 전역변수입니다. - 위치
    //주의사항 - 선언부에는 변수 선언과 초기화를 분리해서 적을 수 없습니다.
    public String name = "강감찬";
    //name = "이성계";
    public static void main(String[] args) {
        String name;//지역변수
        name = "이성계";
        name = "김유신";
        // 뭐야? 같은 이름의 변수를 두 번 선언하는게 가능한가?
        // 전변의 이름과 지변의 이름은 같을 수 있습니다.
        //insert here - name을 출력하면 이성계 | 강감찬
        // 자바의 변수는 한 번에 하나만 기억할 수 있다.
        // 그런데 우리 반 친구들 24명의 이름을 모두 기억하고 싶다면 String[]사용합니다.
        System.out.println(name);//처음엔 이성계이었는데 11번에서 초기화를 다시 하였다. 김유신
        //전역변수를 굳이 main메소드 내부에서 호출하고 싶다면 어떡하지?
        //인스턴스화 - heap메모리 영역에 해당 클래스가 로딩됩니다.
        //그 클래스가 소유하고 있는 변수를 호출할 수 있다.
        // v1은 참조형 변수 입니다. 참조형 변수는 호출하면 값이 아니라 주소번지를 갖는다.
        // 주소번지.변수명으로 호출할 수 있습니다. -> 전역변수
        // 전역변수는 인스턴스변수명(v1) 다음에 닷트연산자(.)를 붙여서 변수명을 호출할 수
        // 있습니다.
        Variable1 v1 = new Variable1();//내 안에 선언된 변수 이더라도 메인메소드 안에서 호출할땐
        System.out.println(v1.name);
    }
}

package kr.twww.mrs.data;

public class User
{
    public enum Gender
    {
        MALE,
        FEMALE
    }

    public enum Age
    {
        UNDER_18,
        BETWEEN_18_24,
        BETWEEN_25_34,
        BETWEEN_35_44,
        BETWEEN_45_49,
        BETWEEN_50_55,
        OVER_55
    }

    public enum Occupation
    {
        OTHER,
	    ACADEMIC_OR_EDUCATOR,
        ARTIST,
        CLERICAL_OR_ADMIN,
        COLLEGE_OR_GRAD_STUDENT,
        CUSTOMER_SERVICE,
        DOCTOR_OR_HEALTH_CARE,
        EXECUTIVE_OR_MANAGERIAL,
        FARMER,
        HOMEMAKER,
        K_12_STUDENT,
        LAWYER,
        PROGRAMMER,
        RETIRED,
        SALES_OR_MARKETING,
        SCIENTIST,
        SELF_EMPLOYED,
        TECHNICIAN_OR_ENGINEER,
        TRADESMAN_OR_CRAFTSMAN,
        UNEMPLOYED,
        WRITER
    }

    public int userId;
    public Gender gender;
    public Age age;
    public Occupation occupation;
    public int zipCode;

    public static Gender ConvertGender( char _gender )
    {
        // TODO: 주어진 성별 텍스트를 enum Gender로 반환
        return null;
    }

    public static Age ConvertAge( int _age )
    {
        // TODO: 주어진 나이 텍스트를 enum Age로 반환
        return null;
    }

    public static Occupation ConvertOccupation( int _occupation )
    {
        // users.dat 파일 불러오기
        // users.dat 파일을 '::' 기준으로 나누고, 4번째 요소인 Occupation 을 enum Occupation으로 반환 후 List[5] 크기 리스트에 담기
        // 리스트 4번째 요소가 Occupation 이므로 List[3] == occupationText 와 비교
        // 둘이 같다면, UserID인 List[0]를 새로운 리스트에 어펜드 TargetUser.append(List[0])
        // return TargetUser
        // ****질문**** 아예 occupationText 를 숫자로 반환하는게 더 효율적이지 않나요?
        // TODO: 주어진 직업 텍스트를 enum Occupation로 반환
        return null;
    }
}

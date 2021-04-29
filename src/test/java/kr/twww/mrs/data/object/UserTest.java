package kr.twww.mrs.data.object;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest
{
    @Test
    public void TestConvertGender()
    {
        assertEquals(User.Gender.MALE, User.ConvertGender("M"));
        assertEquals(User.Gender.FEMALE, User.ConvertGender("F"));
        assertEquals(User.Gender.UNKNOWN, User.ConvertGender(""));
        assertNull(User.ConvertGender("X"));
        assertNull(User.ConvertGender(null));
    }

    @Test
    public void TestConvertAge()
    {
        assertEquals(User.Age.UNDER_18, User.ConvertAge("17"));
        assertEquals(User.Age.BETWEEN_18_24, User.ConvertAge("18"));
        assertEquals(User.Age.BETWEEN_25_34, User.ConvertAge("25"));
        assertEquals(User.Age.BETWEEN_35_44, User.ConvertAge("35"));
        assertEquals(User.Age.BETWEEN_45_49, User.ConvertAge("45"));
        assertEquals(User.Age.BETWEEN_50_55, User.ConvertAge("50"));
        assertEquals(User.Age.OVER_55, User.ConvertAge("99"));
        assertEquals(User.Age.UNKNOWN, User.ConvertAge(""));
        assertNull(User.ConvertAge(null));
    }

    @Test
    public void TestConvertOccupationByText()
    {
        assertEquals(User.Occupation.OTHER, User.ConvertOccupationByText("other"));
        assertEquals(User.Occupation.OTHER, User.ConvertOccupationByText("OtHeR"));
        assertEquals(User.Occupation.OTHER, User.ConvertOccupationByText("OTHER"));
        assertEquals(User.Occupation.K_12_STUDENT, User.ConvertOccupationByText("k-12 student"));
        assertEquals(User.Occupation.COLLEGE_OR_GRAD_STUDENT, User.ConvertOccupationByText("college student"));
        assertEquals(User.Occupation.UNKNOWN, User.ConvertOccupationByText(""));
        assertNull(User.ConvertOccupationByText(null));
    }
}
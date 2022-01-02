package org.hbrs.se2.project.coll.util;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import org.hbrs.se2.project.coll.entities.StudentUser;


public class JobApplicationUtil {

    H2 pageHeadline = new H2("Bewerbung");

    static Span sSalutation = new Span();
    static Span sName = new Span();
    static Span sDateOfBirth = new Span();
    static Span sEmail = new Span();
    static Span sPhone = new Span();

    static Span sAddress = new Span();
    static Span sLocation = new Span();
    static Span sCountry = new Span();

    public static void loadStudentUserInfo(StudentUser studentUser) {
        sSalutation = new Span(studentUser.getSalutation() + " " + studentUser.getTitle());
        sName = new Span(studentUser.getFirstName() + " " + studentUser.getLastName());
        sDateOfBirth = new Span(Utils.convertToGermanDateFormat(studentUser.getDateOfBirth()));
        sEmail = new Span(studentUser.getEmail());
        sPhone = new Span(studentUser.getPhone());

        sAddress = new Span(studentUser.getAddress().getStreet() + " " + studentUser.getAddress().getHouseNumber());
        sLocation = new Span(studentUser.getAddress().getPostalCode() + " " + studentUser.getAddress().getCity());
        sCountry = new Span(studentUser.getAddress().getCountry());
    }
}

package assignment05;
import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.*;

class WebBrowserTest {
//    SinglyLinkedList<URL> loadMe = new SinglyLinkedList<URL>();
//    WebBrowser test1;


    @BeforeEach
    void setUp() throws MalformedURLException {
//        URL link1 = new URL("https://a");
//        URL link2 = new URL("https://b");
//        URL link3 = new URL("https://c");
//
//        loadMe.insertFirst(link1);
//        loadMe.insertFirst(link2);
//        loadMe.insertFirst(link3);
//        WebBrowser test1 = new WebBrowser(loadMe);
    }


    @Test
    void testConstructor() throws MalformedURLException {
        //testing the constructor to see if we can pass it a list of URLs that have been visited
        URL link1 = new URL("https://a");
        URL link2 = new URL("https://b");
        URL link3 = new URL("https://c");

        SinglyLinkedList<URL> loadMe = new SinglyLinkedList<URL>();

        loadMe.insertFirst(link1);
        loadMe.insertFirst(link2);
        loadMe.insertFirst(link3);
        WebBrowser test1 = new WebBrowser(loadMe);

    }


    @Test
    void visit() throws MalformedURLException {
        URL link1 = new URL("https://a");
        URL link2 = new URL("https://b");
        URL link3 = new URL("https://c");

        SinglyLinkedList<URL> loadMe = new SinglyLinkedList<URL>();

        loadMe.insertFirst(link1);
        loadMe.insertFirst(link2);
        loadMe.insertFirst(link3);
        WebBrowser test1 = new WebBrowser(loadMe);

        URL link4 = new URL("https://d");
         test1.visit(link4);
         assertEquals(link4,test1.currentPage);
        System.out.println(test1.backPages.peek());
        //TODO ASK ZIZ ABOUT THIS ONE ^

        URL link5 = new URL("https://e");
        test1.visit(link5);
        assertEquals(link5,test1.currentPage);
        System.out.println(test1.backPages.peek());


    }

    @Test
    void back() throws MalformedURLException {
        URL link1 = new URL("https://a");
        URL link2 = new URL("https://b");
        URL link3 = new URL("https://c");

        SinglyLinkedList<URL> loadMe = new SinglyLinkedList<URL>();

        loadMe.insertFirst(link1);
        loadMe.insertFirst(link2);
        loadMe.insertFirst(link3);
        assertEquals(loadMe.size(),3);

        WebBrowser test1 = new WebBrowser(loadMe);



        URL link4 = new URL("https://d");
        test1.visit(link4);
        URL link5 = new URL("https://e");
        test1.visit(link5);
        assertEquals(test1.currentPage, link5);
        test1.back();
        assertEquals(test1.currentPage,link4);
        test1.back();
        assertEquals(test1.currentPage, link3);
        test1.back();
        assertEquals(test1.currentPage,link2);
        //the next line does throw a NoSuchElementExcpetion, as we wanted, because we ran out of a backstack
        test1.back();
        //test1.back();
        //asserting that we took out all of the back pages
        assertTrue(test1.backPages.isEmpty());
        //asserting that the three pages we visited are in the previous ones
        assertEquals(4,test1.forwardPages.size());
    }

    @Test
    void forward() throws MalformedURLException {
        URL link1 = new URL("https://a");
        URL link2 = new URL("https://b");
        URL link3 = new URL("https://c");

        SinglyLinkedList<URL> loadMe = new SinglyLinkedList<URL>();

        loadMe.insertFirst(link1);
        loadMe.insertFirst(link2);
        loadMe.insertFirst(link3);
        WebBrowser test1 = new WebBrowser(loadMe);


        WebBrowser test2 = new WebBrowser();
        test2.visit(new URL("https://1"));
        test2.visit(new URL("https://2"));
        test2.visit(new URL("https://3"));
        test2.visit(new URL("https://4"));
        test2.back();
        test2.forward();
        assertEquals(test2.currentPage,new URL("https://4"));
        test2.back();
        test2.back();
        test2.forward();
        assertEquals(test2.currentPage,new URL("https://3"));
        test2.back();
        test2.back();
        test2.forward();
        assertEquals(test2.currentPage,new URL("https://2"));
        assertEquals(test2.backPages.size(),2);
    }

    @Test
    void history() throws MalformedURLException {

        URL link1 = new URL("https://a");
        URL link2 = new URL("https://b");
        URL link3 = new URL("https://c");

        SinglyLinkedList<URL> loadMe = new SinglyLinkedList<URL>();

        loadMe.insertFirst(link1);
        loadMe.insertFirst(link2);
        loadMe.insertFirst(link3);
        WebBrowser test1 = new WebBrowser();
        test1.visit(link1);
        test1.visit(link2);
        test1.visit(link3);

        SinglyLinkedList<URL> res = test1.history();

        assertEquals(3, res.size());

    }
}
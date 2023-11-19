package assignment05;

import java.net.URL;
import java.util.NoSuchElementException;

public class WebBrowser {
    LinkedListStack<URL> backPages; //for back button
    LinkedListStack<URL> forwardPages; //for forward button
    public URL currentPage; //current page user is on


    /**
     * empty constructor
     */
    public WebBrowser(){
        backPages = new LinkedListStack<>();
        forwardPages = new LinkedListStack<>();
        currentPage = null;
    }


    /**
     * This constructor creates a new web browser with a preloaded history of visited webpages, given as a list of URLLinks to an external site. objects.
     * The first webpage in the list is the "current" webpage visited, and the remaining webpages are ordered from most recently visited to least recently visited.
     * @param data
     */
    public WebBrowser(SinglyLinkedList<URL> data){
       currentPage =  data.getFirst();
       data.deleteFirst(); //just to make sure we don't accidentally add the current webpage to the history

        backPages = new LinkedListStack<>();
        forwardPages = new LinkedListStack<>();

        for(int i = data.size() - 1; i >=0; i--){
            backPages.push(data.get(i));
        }

    }


    /**
     * This method simulates visiting a webpage, given as a URL.
     * Note that calling this method should clear the forward button stack, since there is no URL to visit next.
     * @param webpage
     */
    public void visit(URL webpage){

        if(currentPage != null){
            backPages.push(currentPage);
        }

        currentPage = webpage;
        forwardPages.clear();
    }

    /**
     * This method simulates using the back button, returning the URL visited.
     * NoSuchElementExceptionLinks to an external site.
     * is thrown if there is no previously-visited URL
     * @return
     * @throws NoSuchElementException
     */
    public URL back() throws NoSuchElementException{
        if(backPages.isEmpty()){
            throw new NoSuchElementException();
        }
        forwardPages.push(currentPage);
        currentPage=(URL) backPages.pop();
         return  currentPage;
    }

    /**
     * This method simulates using the forward button, returning the URL visited.
     * NoSuchElementException is thrown if there is no URL to visit next.
     * @return
     * @throws NoSuchElementException
     */
    public URL forward() throws NoSuchElementException{
            if(forwardPages.isEmpty()){
                throw new NoSuchElementException();
            }
            backPages.push(currentPage);
            currentPage= (URL) forwardPages.pop();
            return currentPage;
    }

    /**
     * his method generates a history of URLs visited, as a list of URL objects ordered from most recently visited to least recently visited (including the "current" webpage visited), without altering subsequent behavior of this web browser.
     * "Forward" URLs are not included.
     * The behavior of the method must be O(N), where N is the number of URLs.
     * @return
     */
    public SinglyLinkedList<URL> history(){
        SinglyLinkedList<URL> historyList = new SinglyLinkedList<>();
        SinglyLinkedList<URL> copy = new SinglyLinkedList<>();

        int iterations = backPages.size();

        for(int i = 0; i < iterations; i++){
            //historyList.insert(i,backPages.pop());
            copy.insertFirst(backPages.pop());

        }

        for(int i = iterations - 1; i >=0; i--){
            URL temp = copy.getFirst();
            historyList.insertFirst(temp);
            backPages.push(temp);
            copy.deleteFirst();
        }

//        for(int i = historyList.size() - 1; i >= 0 ; i--){
//            backPages.push(historyList.get(i));
//        }

        historyList.insertFirst(currentPage);

        return historyList;
    }


    }//end of class brackets!!!






package de.codecentric.worblehat.acceptancetests.adapter.wrapper;

public enum PageElement {
    ADDBOOKBUTTON("addBook"), BOOKLIST("bookList"), BORROWBOOKBUTTON("borrowBook"), ISBNERROR("isbn-error"),
    RETURNALLBOOKSBUTTON("returnAllBooks"),RETURNBOOKBUTTON("returnBook"), SHOWMYBOOKSBUTTON("showMyBooks"), ERROR("error");


    private String elementId;

    PageElement(String elementId){
        this.elementId = elementId;
    }

    public String getElementId(){
        return elementId;
    }
}

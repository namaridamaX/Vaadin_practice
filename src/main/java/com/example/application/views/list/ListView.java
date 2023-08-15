package com.example.application.views.list;

import com.example.application.data.entity.Contact;
import com.example.application.data.service.CrmService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Collections;

@Route(value = "")
@PageTitle("Contacts | Vaadin CRM")
public class ListView extends VerticalLayout { // 垂直にレイアウトを配置する
    Grid<Contact> grid = new Grid<>(Contact.class); // グリッド（表）の定義
    TextField filterText = new TextField(); // テキスト用フィールドの確保
    ContactForm form; // コンタクトフォームの定義
    CrmService service; // サービスの定義

    //コンストラクター
    public ListView(CrmService crmService) {
        this.service = crmService;
        addClassName("list-view");
        setSizeFull(); // 最大化
        configureGrid(); // グリッドを初期化するメソッド
        configureForm(); // フォームを初期化するメソッド

        //add(getToolbar(), grid);
        add(getToolbar(), getContent());
        updateList(); // リストを更新するメソッド
    }

    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns("firstName", "lastName", "email");
        grid.addColumn(contact -> contact.getStatus().getName()).setHeader("Status");
        grid.addColumn(contact -> contact.getCompany().getName()).setHeader("Company");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid); // グリッドにフォームの２倍のスペースが必要であることを指定
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new ContactForm(Collections.emptyList(), Collections.emptyList());
        form.setWidth("25em");
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        // filterTextに変更が入った際に、updateList()を呼び出す
        // リストを更新するメソッド
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add contact");

        var toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void updateList() {
        // filterTextの値を使って、連絡先を検索する
        grid.setItems(service.findAllContacts(filterText.getValue()));
    }
}
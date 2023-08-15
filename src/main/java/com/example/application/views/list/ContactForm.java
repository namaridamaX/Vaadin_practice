package com.example.application.views.list;


import com.example.application.data.entity.Company;
import com.example.application.data.entity.Status;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;


public class ContactForm extends FormLayout { // ビューポートの幅に応じて、フォームフィールドを1列または２列に表示するレスポンシブレイアウト

    // テキストフィールドの準備
    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    EmailField email = new EmailField("Email");
    // コンボボックスの準備
    ComboBox<Status> status = new ComboBox<>("Status");
    ComboBox<Company> company = new ComboBox<>("Company");

    // ボタンの準備
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public ContactForm(List<Company> companies, List<Status> statuses) {
        addClassName("contact-form");// CSSのクラス名を追加

        company.setItems(companies);
        company.setItemLabelGenerator(Company::getName);
        status.setItems(statuses);
        status.setItemLabelGenerator(Status::getName);

        // すべてのコンポーネントを レイアウトに追加
        // ボタンだけ処理が別で必要なので、createButtonLayout()を呼び出す
        add(firstName,
            lastName,
            email,
            company,
            status,
            createButtonLayout());
    }

    // 水平方向にコンポーネントを配置するメソッド
    private HorizontalLayout createButtonLayout() {
        // ボタンのデザインを設定
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        // キーボードショートカットの設定
        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        return new HorizontalLayout(save, delete, close); // ボタンを水平方向に配置
    }
}

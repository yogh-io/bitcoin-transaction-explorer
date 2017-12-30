package nl.yogh.gwt.wui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Widget;
import com.yoghurt.crypto.transactions.client.resources.R;

public class ThemeSwitcher extends Composite {
  private static final ThemeSwitcherUiBinder UI_BINDER = GWT.create(ThemeSwitcherUiBinder.class);

  interface ThemeSwitcherUiBinder extends UiBinder<Widget, ThemeSwitcher> {}

  public interface CustomStyle extends CssResource {
    String switchToDark();

    String switchToLight();
  }

  @UiField FocusPanel themeButton;

  @UiField CustomStyle style;

  public ThemeSwitcher() {
    initWidget(UI_BINDER.createAndBindUi(this));
  }

  @UiHandler("themeButton")
  public void onThemeClick(final ClickEvent e) {
    final boolean switchToLight = Document.get().getDocumentElement().getClassName().contains(R.css().bodyDark());

    themeButton.setStyleName(style.switchToDark(), switchToLight);
    themeButton.setStyleName(style.switchToLight(), !switchToLight);

    Document.get().getDocumentElement().setClassName(switchToLight ? R.css().bodyLight() : R.css().bodyDark());
  }
}

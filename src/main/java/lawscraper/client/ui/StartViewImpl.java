package lawscraper.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.*;
import lawscraper.shared.proxies.LawProxy;


public class StartViewImpl extends Composite implements StartView {
    private static StartViewImplUiBinder uiBinder = GWT.create(StartViewImplUiBinder.class);

    interface StartViewImplUiBinder extends UiBinder<Widget, StartViewImpl> {
    }

    @UiField Button sendButton;
    @UiField Button scrapeButton;
    @UiField FlowPanel container;
    @UiField Label processingLabel;
    private Presenter listener;
    private String name;

    public StartViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @UiHandler("sendButton")
    void onClickGoodbye(ClickEvent e) {
        listener.getLaw();
    }

    @UiHandler("scrapeButton")
    void onClickScrapeButton(ClickEvent e) {
        listener.scrapeLaw();
    }

    @Override
    public void setPresenter(Presenter listener) {
        this.listener = listener;
    }

    @Override
    public void setLaw(LawProxy law) {
        container.clear();
        container.add(new HTMLPanel(law.getTitle()));

        /*
        Set<Divider> dividers = law.getDividers();
        if (dividers.size() == 0) {
            DisclosurePanel currentDividerDP = createSectionPanel("Tom avdelning");
            currentDividerDP.getElement().getStyle().setWidth(60, Style.Unit.PCT);
            FlowPanel chaptersContainer = new FlowPanel();

            for (Chapter chapter : law.getChapters()) {
                DisclosurePanel currentChapterDP = createSectionPanel(chapter.getHeadLine());
                FlowPanel paragraphsContainer = new FlowPanel();
                for (Paragraph paragraph : chapter.getParagraphs()) {
                    DisclosurePanel currentParagraphDP = createSectionPanel("§" + paragraph.getParagraphNo());
                    FlowPanel sectionsContainer = new FlowPanel();
                    for (Section section : paragraph.getSections()) {
                        DisclosurePanel currentSectionDP = createSectionPanel("Stycke " + section.getSectionNo());
                        currentSectionDP.setOpen(true);

                        FlowPanel flowPanel = new FlowPanel();
                        flowPanel.add(new HTMLPanel(section.getSectionText()));
                        currentSectionDP.setContent(flowPanel);
                        setSectionDP(currentSectionDP);
                        sectionsContainer.add(currentSectionDP);
                    }
                    currentParagraphDP.setContent(sectionsContainer);
                    paragraphsContainer.add(currentParagraphDP);

                }
                currentChapterDP.setContent(paragraphsContainer);
                chaptersContainer.add(currentChapterDP);
            }
            currentDividerDP.setContent(chaptersContainer);
            container.add(currentDividerDP);

        } else {
            for (Divider divider : dividers) {
                DisclosurePanel currentDividerDP = createSectionPanel(divider.getHeadline());

                FlowPanel chaptersContainer = new FlowPanel();
                for (Chapter chapter : divider.getChapters()) {
                    DisclosurePanel currentChapterDP = createSectionPanel(chapter.getHeadLine());
                    FlowPanel paragraphsContainer = new FlowPanel();
                    for (Paragraph paragraph : chapter.getParagraphs()) {
                        DisclosurePanel currentParagraphDP = createSectionPanel("§" + paragraph.getParagraphNo());
                        FlowPanel sectionsContainer = new FlowPanel();
                        for (Section section : paragraph.getSections()) {
                            DisclosurePanel currentSectionDP = createSectionPanel("Stycke " + section.getSectionNo());
                            currentSectionDP.setOpen(true);

                            FlowPanel flowPanel = new FlowPanel();
                            flowPanel.add(new HTMLPanel(section.getSectionText()));
                            currentSectionDP.setContent(flowPanel);
                            setSectionDP(currentSectionDP);
                            sectionsContainer.add(currentSectionDP);
                        }
                        currentParagraphDP.setContent(sectionsContainer);
                        paragraphsContainer.add(currentParagraphDP);

                    }
                    currentChapterDP.setContent(paragraphsContainer);
                    chaptersContainer.add(currentChapterDP);
                }
                currentDividerDP.setContent(chaptersContainer);
                container.add(currentDividerDP);
            }
        }
        */
    }

    @Override
    public void setScrapeUpdate(String message) {
        String update = processingLabel.getText() + "\n" + message + "\n";
        processingLabel.setText(update + "<br>");
    }

    private void setSectionDP(final DisclosurePanel currentSectionDP) {
        currentSectionDP.getHeader().getElement().getStyle().setPosition(Style.Position.RELATIVE);
        currentSectionDP.getHeader().getElement().getStyle().setFloat(Style.Float.RIGHT);
        currentSectionDP.getHeader().getElement().getStyle().setFontStyle(Style.FontStyle.ITALIC);
        final FlowPanel content = (FlowPanel) currentSectionDP.getContent();

        content.addDomHandler(new MouseOverHandler() {
            @Override
            public void onMouseOver(MouseOverEvent event) {
                content.getElement().getStyle().setBackgroundColor("lightgrey");
            }
        }, MouseOverEvent.getType());

        content.addDomHandler(new MouseOutHandler() {
            @Override
            public void onMouseOut(MouseOutEvent event) {
                content.getElement().getStyle().setBackgroundColor("white");
            }
        }, MouseOutEvent.getType());
    }

    private DisclosurePanel createSectionPanel(String headline) {
        HTMLPanel titleHtmlPanel = new HTMLPanel(headline);
        DisclosurePanel disclosurePanel = new DisclosurePanel();
        disclosurePanel.setHeader(titleHtmlPanel);
        return disclosurePanel;
    }
}

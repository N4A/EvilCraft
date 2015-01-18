package evilcraft.infobook;

import com.google.common.collect.Lists;
import evilcraft.client.gui.container.GuiOriginsOfDarkness;
import evilcraft.core.helper.L10NHelpers;
import evilcraft.core.helper.RenderHelpers;
import net.minecraft.client.gui.FontRenderer;
import org.lwjgl.opengl.GL11;

import java.util.Collections;
import java.util.List;

/**
 * Section of the info book.
 * Can have child sections.
 * @author rubensworks
 */
public class InfoSection {

    private static final int X_OFFSET = 16;
    private static final int Y_OFFSET = 16;
    private static final int TITLE_LINES = 3;

    private InfoSection parent;
    private int childIndex;
    private String unlocalizedName;
    private List<InfoSection> sections = Lists.newLinkedList();
    private List<List<HyperLink>> links = Lists.newLinkedList();;
    private List<String> paragraphs;
    private List<String> localizedPages;

    public InfoSection(InfoSection parent, int childIndex, String unlocalizedName, List<String> paragraphs) {
        this.parent = parent;
        this.childIndex = childIndex;
        this.unlocalizedName = unlocalizedName;
        this.paragraphs = paragraphs;
    }

    /**
     * Will make a localized version of this section with a variable amount of paragraphs.
     * Must be called once before the section will be drawn.
     */
    public void bakeSection(FontRenderer fontRenderer, int width, int maxLines, int lineHeight) {
        if(paragraphs.size() == 0) {
            // TODO: buttons for returning to an index (shift click on back-button to go back to first page of this section)
            // TODO: 2 pages at once?
            // Add hyperlinks for these sections (page-wrapped)
            int linesOnPage = 0;
            if(isTitlePage(0)) {
                linesOnPage += TITLE_LINES;
            }
            List<HyperLink> pageLinks = Lists.newArrayListWithCapacity(maxLines);
            StringBuilder lines = new StringBuilder();
            for(InfoSection infoSection : sections) {
                lines.append("\n");
                linesOnPage++;
                if(linesOnPage >= maxLines) {
                    linesOnPage = 0;
                    links.add(pageLinks);
                    pageLinks = Lists.newArrayListWithCapacity(maxLines);
                }
                pageLinks.add(new HyperLink(X_OFFSET, Y_OFFSET + (linesOnPage - 1) * lineHeight, infoSection));
            }
            paragraphs.add(lines.toString());
            links.add(pageLinks);
        }

        // Localize paragraphs and fit them into materialized paragraphs.
        String contents = "";
        for(String paragraph : paragraphs) {
            contents += L10NHelpers.localize(paragraph) + "\n\n";
        }

        // Wrap the text into pages.
        List<String> allLines = fontRenderer.listFormattedStringToWidth(contents, width - X_OFFSET);
        localizedPages = Lists.newLinkedList();
        int linesOnPage = 0;
        StringBuilder currentPage = new StringBuilder();
        if(isTitlePage(0)) {
            for(int i = 1; i < TITLE_LINES; i++) currentPage.append("\n"); // Make a blank space for the section title.
            linesOnPage += TITLE_LINES;
        }
        for(String line : allLines) {
            linesOnPage++;
            if(linesOnPage >= maxLines) {
                linesOnPage = 0;
                localizedPages.add(currentPage.toString());
                currentPage = new StringBuilder();
            }
            if(linesOnPage > 1) currentPage.append("\n");
            currentPage.append(line);
        }
        localizedPages.add(currentPage.toString());
    }

    public boolean isTitlePage(int page) {
        return this.unlocalizedName != null && page == 0;
    }

    public void registerSection(InfoSection section) {
        sections.add(section);
    }

    public int getPages() {
        return localizedPages.size();
    }

    protected String getLocalizedPageString(int page) {
        return localizedPages.get(page);
    }

    public String getLocalizedTitle() {
        return L10NHelpers.localize(unlocalizedName);
    }

    public int getSubSections() {
        return sections.size();
    }

    public InfoSection getSubSection(int index) {
        return sections.get(index);
    }

    public InfoSection getParent() {
        return this.parent;
    }

    public boolean isRoot() {
        return getParent() == null;
    }

    public int getChildIndex() {
        return this.childIndex;
    }

    public List<HyperLink> getLinks(int page) {
        if(links.size() <= page) return Collections.EMPTY_LIST;
        return links.get(page);
    }

    public void drawScreen(GuiOriginsOfDarkness gui, int x, int y, int width, int height, int page) {
        FontRenderer fontRenderer = gui.getFontRenderer();
        boolean oldUnicode = fontRenderer.getUnicodeFlag();
        fontRenderer.setUnicodeFlag(true);
        fontRenderer.setBidiFlag(false);
        String content = getLocalizedPageString(page);
        fontRenderer.drawSplitString(content, x + X_OFFSET, y + Y_OFFSET, width - X_OFFSET, 0);

        if(isTitlePage(page)) {
            GL11.glPushMatrix();
            float scale = 1.5f;
            GL11.glScalef(scale, scale, 1.0f);
            String title = getLocalizedTitle();
            int titleLength = fontRenderer.getStringWidth(title);
            fontRenderer.drawString(title, Math.round((x + width / 2) / scale - titleLength / 2), Math.round((y + Y_OFFSET + 3) / scale), RenderHelpers.RGBToInt(120, 20, 30));
            GL11.glPopMatrix();
            gui.drawHorizontalRule(x + width / 2, y + Y_OFFSET);
            gui.drawHorizontalRule(x + width / 2, y + Y_OFFSET + 21);
        }
        fontRenderer.setUnicodeFlag(oldUnicode);
    }

}
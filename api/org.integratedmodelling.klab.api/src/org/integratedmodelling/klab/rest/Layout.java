package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

/**
 * Sent from the back end to set up the interface. A Layout is a component with 0 or 1 header panel,
 * 0 or 1 footer panel, 0 or more panels in the right, center and left sections. The name of the
 * view is the name of the behavior that specifies it.
 * 
 * 
 * Input from Enrico on suggested conventions:
 * 
 * <pre>
 * --------------------------------------------------------------------------------------------------------
 * View
Componente principale e contenitore di tutto il resto
Si struttura in 5 aree:

    Header (max 1 elemento)
    Left (n elementos)
    Right (n elementos)
    Center (n elementos)
    Footer (max 1 elemento)

Attributi
Guardando la definizione che usa Quasar di Layout (che � la cosa corrispondente) ci sono 2 cose interessanti che magari verrebbero bene pi� avanti:
- Un Layout pu� essere un container quindi pu� passare ad essere pure lui un componente. Nel caso di Quasar, questo fa s� che le dimensioni facciano riferimento alla p�gina intera o al Panel dove � messo (per maggiori dettagli mi dici). Per noi questo potrebbe essere utile pensando a una view generale che sostituisca l'explorer come ben dicevi tu
- Usano una serie di lettere per gestire le sovrapposizioni:
Fondamentalmente si gestisce se i pannelli laterali coprono o no l'header e il footer

Panel
Header
Footer
Non so se Header e Footer sono rimasugli di altre implementazioni, pero credo che non sono necessari.
Quasar ha dei componenti specifici per� fondamentalmente gestisce dettagli estetici. Un panel messo nella propriet� header di View non penso abbia bisogno di ulteriori dettagli, quindi mi centrerei in Panel
Panel � un contenitore di elementi eterogenei posizionati a seconda di come si definiscono e del layout previsto per il pannello,
Un pannello pu� contenere altri pannelli e cos� successivamente
Personalmente credo che tutti i componenti dovrebbero essere contenuti in un pannello e non possano essere lasciati soli, pi� che altro per essere un po' coerenti
Attributi
visible: visibilit� che p�o essere legata a una variabile
layout: qualche descrizione sul tipo di layout. Qua ci si pu� mettere di tutto, per� in un principio con orizzontale, verticale ed indicare se puoi andare a capo dovrebbe essere sufficiente. La storia del a capo � per sapere se si cambia la dimensione degli elementi per starci o quando non ci si sta si va a nuova linea. 
Magari si pu� anche pensare in un GridLayout o in un Flex pi� avanti.

Group
Un gruppo credo dovrebbe essere qualcosa di omogeneo per poter gestire elementi come se fossero una unit�. � necessario per i radioButton e i checkButton
Attributi
Credo che possono essere gli stessi di un componente:

PushButton
CheckButton
RadioButton
TextInput
Combo
Tree
TreeItem
Map
Componenti che possono essere inseriti in un Panel
In quanto ad attributi, metterei quelli fissi che possono essere validi anche per i gruppi
align
width: qui userei o percentuale o cose fisse senza dimensioni specifiche (xs, s, m, etc) e poi lo stile li definisce
Ed in questo momento non mi viene in mente alto
E poi lascerei una serie di attributi come un Map visto che il funzionamento di ogni componente avr� le sue necessita specifiche

Alert
Confirm
Questi due li stiamo trattando in una maniera speciale, penso che potranno avere un panel e dentro ci sia quello che vuoi
Sarebbero pi� simili ad una View pero con una parte dove metti il panel con il contenuto, e una parte con i bottoni specifici (alert solo ok, confirm ok e cancel)
Oppure un tipo Dialog que pu� avere delle implementazioni per alert e confirm
--------------------------------------------------------------------------------------------------------
 * </pre>
 * 
 * @author Ferd
 *
 */
public class Layout extends ViewComponent {

    public static enum Workspace {
        Main, Window, Stack, Modal
    }

    public static class MenuItem {

        private String text; // a slash is used to define nested menus
        private String id;
        private String url;

        public String getText() {
            return text;
        }
        public void setText(String text) {
            this.text = text;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
    }

    private List<ViewPanel> panels = new ArrayList<>();
    private List<ViewPanel> leftPanels = new ArrayList<>();
    private List<ViewPanel> rightPanels = new ArrayList<>();
    private ViewPanel header;
    private ViewPanel footer;
    private String receivingIdentity;
    private String label;
    private String description;
    private String logo;
    private String versionString;
    private String projectId;
    private String styleSpecs = null;
    private Workspace workspace = Workspace.Main;
    private List<MenuItem> menu = new ArrayList<>();

    // this is for layout management in clients, not API
    private int index;

    public Layout() {
        setType(Type.View);
    }

    public Layout(String behaviorName, String applicationId) {
        this();
        setName(behaviorName);
        setApplicationId(applicationId);
    }

    public List<ViewPanel> getPanels() {
        return panels;
    }

    public void setPanels(List<ViewPanel> panels) {
        this.panels = panels;
    }

    public ViewPanel getHeader() {
        return header;
    }

    public void setHeader(ViewPanel header) {
        this.header = header;
    }

    public ViewPanel getFooter() {
        return footer;
    }

    public void setFooter(ViewPanel footer) {
        this.footer = footer;
    }

    public List<ViewPanel> getLeftPanels() {
        return leftPanels;
    }

    public void setLeftPanels(List<ViewPanel> leftPanels) {
        this.leftPanels = leftPanels;
    }

    public List<ViewPanel> getRightPanels() {
        return rightPanels;
    }

    public void setRightPanels(List<ViewPanel> rightPanels) {
        this.rightPanels = rightPanels;
    }

    public boolean empty() {
        return panels.size() == 0 && footer == null && header == null && rightPanels.size() > 0 && leftPanels.size() > 0;
    }

    public String getReceivingIdentity() {
        return receivingIdentity;
    }

    public void setReceivingIdentity(String receivingIdentity) {
        this.receivingIdentity = receivingIdentity;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getStyleSpecs() {
        return styleSpecs;
    }

    public void setStyleSpecs(String styleSpecs) {
        this.styleSpecs = styleSpecs;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public String getVersionString() {
        return versionString;
    }

    public void setVersionString(String versionString) {
        this.versionString = versionString;
    }

    public List<MenuItem> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuItem> menu) {
        this.menu = menu;
    }
}

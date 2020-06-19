package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

/**
 * Sent from the back end to set up the interface. A Layout is a component with 0
 * or 1 header panel, 0 or 1 footer panel, 0 or more panels in the right, center
 * and left sections. The name of the view is the name of the behavior that
 * specifies it.
 * 
 * 
 * Input from Enrico on suggested conventions:
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
Guardando la definizione che usa Quasar di Layout (che è la cosa corrispondente) ci sono 2 cose interessanti che magari verrebbero bene più avanti:
- Un Layout puó essere un container quindi puó passare ad essere pure lui un componente. Nel caso di Quasar, questo fa sí che le dimensioni facciano riferimento alla página intera o al Panel dove è messo (per maggiori dettagli mi dici). Per noi questo potrebbe essere utile pensando a una view generale che sostituisca l'explorer come ben dicevi tu
- Usano una serie di lettere per gestire le sovrapposizioni:
Fondamentalmente si gestisce se i pannelli laterali coprono o no l'header e il footer

Panel
Header
Footer
Non so se Header e Footer sono rimasugli di altre implementazioni, pero credo che non sono necessari.
Quasar ha dei componenti specifici però fondamentalmente gestisce dettagli estetici. Un panel messo nella proprietà header di View non penso abbia bisogno di ulteriori dettagli, quindi mi centrerei in Panel
Panel è un contenitore di elementi eterogenei posizionati a seconda di come si definiscono e del layout previsto per il pannello,
Un pannello puó contenere altri pannelli e cosí successivamente
Personalmente credo che tutti i componenti dovrebbero essere contenuti in un pannello e non possano essere lasciati soli, più che altro per essere un po' coerenti
Attributi
visible: visibilità che púo essere legata a una variabile
layout: qualche descrizione sul tipo di layout. Qua ci si puó mettere di tutto, però in un principio con orizzontale, verticale ed indicare se puoi andare a capo dovrebbe essere sufficiente. La storia del a capo è per sapere se si cambia la dimensione degli elementi per starci o quando non ci si sta si va a nuova linea. 
Magari si puó anche pensare in un GridLayout o in un Flex più avanti.

Group
Un gruppo credo dovrebbe essere qualcosa di omogeneo per poter gestire elementi come se fossero una unità. È necessario per i radioButton e i checkButton
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
E poi lascerei una serie di attributi come un Map visto che il funzionamento di ogni componente avrà le sue necessita specifiche

Alert
Confirm
Questi due li stiamo trattando in una maniera speciale, penso che potranno avere un panel e dentro ci sia quello che vuoi
Sarebbero piú simili ad una View pero con una parte dove metti il panel con il contenuto, e una parte con i bottoni specifici (alert solo ok, confirm ok e cancel)
Oppure un tipo Dialog que puó avere delle implementazioni per alert e confirm
--------------------------------------------------------------------------------------------------------
 * </pre>
 * 
 * @author Ferd
 *
 */
public class Layout extends ViewComponent {

	public static final String DEFAULT_PANEL_NAME = "defaultpanel";

	private List<ViewPanel> panels = new ArrayList<>();
	private List<ViewPanel> leftPanels = new ArrayList<>();
	private List<ViewPanel> rightPanels = new ArrayList<>();
	private ViewPanel header;
	private ViewPanel footer;
	private String receivingIdentity;
	private String label;
	private String description;
	private String logo;
	private String projectId;
	
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
		return panels.size() == 0 && footer == null && header == null && rightPanels.size() > 0
				&& leftPanels.size() > 0;
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
}

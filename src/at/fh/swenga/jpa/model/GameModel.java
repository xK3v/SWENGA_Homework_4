package at.fh.swenga.jpa.model;
 
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;
 
@Entity
@Table(name = "Game")
 
public class GameModel implements java.io.Serializable {
 
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
 
	@Column(nullable = false, length = 30)
	private String name;
 
	@Column(nullable = false, length = 30)
	private String developer;
 
	@OneToOne(cascade = CascadeType.ALL)
	private DocumentModel document;
 
	@Version
	long version;
 
	public GameModel() {
	}
 
	public GameModel(String name, String developer) {
		super();
		this.name = name;
		this.developer = developer;
	}
 
	public int getId() {
		return id;
	}
 
	public void setId(int id) {
		this.id = id;
	}
 
	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
 
	public String getDeveloper() {
		return developer;
	}
 
	public void setDeveloper(String developer) {
		this.developer = developer;
	}
 
	public DocumentModel getDocument() {
		return document;
	}
 
	public void setDocument(DocumentModel document) {
		this.document = document;
	}
 
 
 
}
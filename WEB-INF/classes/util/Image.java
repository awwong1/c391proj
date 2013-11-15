package util;


public class Image {
	int photo_id;
	String ownername;
	String dateUploaded;
	String location;
	String subject;
	String description;
	int permitted;
	BLOB thumbnail;
	BLOB photo;


	/*
	 * Constructor 
	 * @param photo_id
	 * @param ownername
	 * @param dateUploaded
	 * @param location
	 * @param subject
	 * @param description
	 * @param permitted
	 * @param thumbnail
	 * @param photo
	*/
	public Image(int photo_id, String ownername, String dateUploaded, String location,
		     String subject, String description, int permitted, BLOB thumbnail,
		     BLOB photo) {
		this.photo_id = photo_id;
		this.ownername = ownername;
		this.dateUploaded = dateUploaded;
		this.location = location;
		this.subject = subject;
		this.description = description;
		this.permitted = permitted;
		this.thumbnail = thumbnail;
		this.photo = photo;
	}

	/*
	 * Getter and Setters
	 */
	public int getPhotoId() {return photo_id;}
	public void setPhotoId() {this.photo_id = photo_id;}
	public String getOwnerName() {return ownername;}
	public void setOwnerName() {this.ownername = ownername;}
	public String getDate() {return dateUploaded;}
	public void setDateUploaded() {this.dateUploaded = dateUploaded;}
	public String getLocation() {return location;}
	public void setLocation() {this.location = location;}
	public String getDescription() {return description;}
	public void setDescription() {this.description = description;}
	public int getPermitted() {return permitted;}
	public void setPermitted() {this.permitted = permitted;}
	public BLOB getThumbnail() {return thumbnail;}
	public void setThumbnail() {this.thumbnail = thumbnail;}
	public BLOB getPhoto() {return photo;}
	public void setPhoto() {this.photo = photo;}
}



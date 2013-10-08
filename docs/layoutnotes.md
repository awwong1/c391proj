# Layout

For each required module, some sample pages and descriptions.

### User Management Module
* /login.html
Allows user to log in using username and password. Simple form for filling out required fields. Will create session for client.
* /signup.html
Allows user to register for an account. 
> + unique username
> + password
> + first name
> + last name
> + address
> + email
> + phone number
* /account.html
Allows user to modify his or her own account information.

All other modules will assume that the user is logged in. 

### Security Module
Manages the session variable(s)
Manages the photo viewing permissions
> + public
> + designated group
> + private
* /groups/makegroup.html
Allows a registered user to create a group
> + group name
> + list of users within the group
* /groups/viewgroups.html
View all groups associated to the user
* groups/modifygroup.html
Allows a registered user to modify a group which the user owns

### Uploading Module
* /photos/upload.html
Allows a user to upload images to the database
> + one image stored as a local file
> + all images stored in a local directory
After photo(s) are uploaded, following information may be modified
> + where the image was taken
> + when the image was taken
> + image subject
> + image description
> + access permissions

### Display Module
* /
> Will be the root directory (index.html)
> Displays image thumbnails in a grid, according to current user permissions.
> Will display top 5 photos.
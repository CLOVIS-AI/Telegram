# Telegram Bot API by CLOVIS

## Using the library

### Installation

 1. Choose where you want to save the library
 2. Execute the following command, or download and extract ZIP from GitHub :
		git clone --recursive http://github.com/clovis-ai/Telegram.git WHERE

If you have forgotten the `--recursive` option, go to "Troubleshooting" > "Submodule is empty" to find a solution.

### Edit source code

If you want to, you can check out / change the API source code ; do these steps:

#### NetBeans

 1. Open NetBeans
 2. (Optional: only if you want to see/change the source code) File > Open project > Choose the API you just downloaded

### Updating the API

Because we use Git, updating the API is very straightforward. Go into the folder you cloned into (see Installation) and execute the following command:
		git pull

The API will not prompt you when you have not updated for too long, so remember to do it every once and again. If you want to choose a special API version, we encourage you to learn more about Git.

### Creation of a project

#### NetBeans

 1. Open NetBeans
 2. Create your project as you usually do
 3. In the "project" tab, right-click on "libraries", select "add project"
 4. Choose the Telegram API you just cloned

## Troubleshooting

### Submodule (minimal-json folder) is empty

You likely forgot the --recursive option in the clone command.

Fix it using :
		git submodule init
		git submodule update
If you are using Linux, you can also use the `init.sh` script at the root of the project.

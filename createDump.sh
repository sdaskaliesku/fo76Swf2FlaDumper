# path to Archive2.exe for Fo4 Creation Kit
ARCHIVE2_TOOL="D:\\Program Files (x86)\\Bethesda.net Launcher\\games\\Tools\\Archive2\\Archive2.exe"
# path to SeventySix - Interface.ba2 file
PATH_TO_BA2_FILE="E:\\BethesdaGames\\Fallout76\\Data\\SeventySix - Interface.ba2"
# path to FFDEC bat file
PATH_TO_FFDEC="D:\\Program Files (x86)\\FFDec\\ffdec.bat"
# path to unzipped swf files, they should be removed after converting to fla
PATH_TO_TMP_SWF_DIR="output"
# actual output with all of the *.as files and *.swf (and whatever is included in swf)
PATH_TO_FLA_OUTPUT_DIR="dump"
# directories to scan inside SeventySix - Interface.ba2
BA2_SUBDIRS="interface,programs"
./mvnw clean package exec:java -DARCHIVE2_TOOL="$ARCHIVE2_TOOL" -DPATH_TO_BA2_FILE="$PATH_TO_BA2_FILE" -DPATH_TO_FFDEC="$PATH_TO_FFDEC" -DPATH_TO_TMP_SWF_DIR="$PATH_TO_TMP_SWF_DIR" -DPATH_TO_FLA_OUTPUT_DIR="$PATH_TO_FLA_OUTPUT_DIR" -DBA2_SUBDIRS="$BA2_SUBDIRS"
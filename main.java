import java.util.*;

class MyFile {
  private final UUID id = UUID.randomUUID();
  private String name;
  private String content;
  private Date createdAt;
  private int size;
  private boolean isHidden;
  private boolean isDeleted;
  private String owner;
  private String permissions;
  private List<String> accessLogs;
}

class Folder {
  private final UUID id = UUID.randomUUID();
  private String name;
  private Folder parent;
  private Date createdAt;
  private Date updatedAt;
  private String owner;
  private String permissions;
  private boolean isHidden;
  private boolean isDeleted;
  private List<String> accessLogs;

  ArrayList<Folder> subFolders;
  HashMap<String, MyFile> files;
}

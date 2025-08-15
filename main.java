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

  public MyFile(String name, String content, String owner, String permissions) {
    this.name = name;
    this.content = content;
    this.createdAt = new Date();
    this.size = content.length();
    this.isHidden = false;
    this.isDeleted = false;
    this.owner = owner;
    this.permissions = permissions;
    this.accessLogs = new ArrayList<>();
    accessLogs.add("File created by " + owner + " at " + createdAt);
  }

  public boolean isDeleted() {
    return isDeleted;
  }

  public void setDeleted(boolean deleted) {
    isDeleted = deleted;
  }

  public boolean isHidden() {
    return isHidden;
  }

  public void setHidden(boolean hidden) {
    isHidden = hidden;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
    accessLogs.add("File renamed to " + name + " at " + new Date());
  }

  public String getContent() {
    accessLogs.add("File read at " + new Date());
    return content;
  }

  public void setContent(String content) {
    this.content = content;
    this.size = content.length();
    accessLogs.add("Content updated at " + new Date());
  }

  public void addAccessLog(String log) {
    accessLogs.add(log + " at " + new Date());
  }

  public void printLogs() {
    System.out.println("Access Logs for file: " + name);
    for (String log : accessLogs) {
      System.out.println(log);
    }
  }

  public int getSize() {
    return size;
  }

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

  public Folder(String name, String owner, String permissions) {
    this.name = name;
    this.owner = owner;
    this.permissions = permissions;
    this.createdAt = new Date();
    this.updatedAt = this.createdAt;
    this.isHidden = false;
    this.isDeleted = false;
    this.subFolders = new ArrayList<>();
    this.files = new HashMap<>();
    this.accessLogs = new ArrayList<>();
    accessLogs.add("Folder created at " + createdAt);
  }

  private void addAccessLog(String log) {
    if (accessLogs == null) {
      accessLogs = new ArrayList<>();
    }
    accessLogs.add(log + " at " + new Date());
  }

  public void createFile(String name, String content, String owner, String permissions) {
    if (files.containsKey(name)) {
      System.out.println("File already exists with the name: " + name);
      return;
    }

    MyFile file = new MyFile(name, content, owner, permissions);
    files.put(name, file);
    addAccessLog("File created: " + name);
  }

  public void writeToFile(String name, String newContent) {
    MyFile file = files.get(name);
    if (file == null || file.isDeleted()) {
      System.out.println("File not found.");
      return;
    }
    file.setContent(newContent);
    addAccessLog("File written: " + name);
  }

  public void readFile(String name) {
    MyFile file = files.get(name);
    if (file == null || file.isDeleted() || file.isHidden()) {
      System.out.println("File not available.");
      return;
    }
    System.out.println("File content:\n" + file.getContent());
  }

  public void deleteFile(String name) {
    MyFile file = files.get(name);
    if (file == null || file.isDeleted()) {
      System.out.println("File not found.");
      return;
    }
    file.setDeleted(true);
    addAccessLog("File deleted: " + name);
  }

  public void hideFile(String name) {
    MyFile file = files.get(name);
    if (file != null) {
      file.setHidden(true);
      file.addAccessLog("File hidden: " + name);
    }
  }

  public void unhideFile(String name) {
    MyFile file = files.get(name);
    if (file != null) {
      file.setHidden(false);
      file.addAccessLog("File unhidden: " + name);
    }
  }

  public void renameFile(String oldName, String newName) {
    if (!files.containsKey(oldName)) {
      System.out.println("Original file not found.");
      return;
    }
    if (files.containsKey(newName)) {
      System.out.println("A file with the new name already exists.");
      return;
    }

    MyFile file = files.remove(oldName);
    file.setName(newName);
    files.put(newName, file);
    addAccessLog("File renamed from " + oldName + " to " + newName);
  }

  public void printFileLogs(String name) {
    MyFile file = files.get(name);
    if (file != null) {
      file.printLogs();
    } else {
      System.out.println("File not found.");
    }
  }

  public void getFileSize(String name) {
    MyFile file = files.get(name);
    if (file != null) {
      System.out.println("Size of file '" + name + "': " + file.getSize() + " bytes");
    } else {
      System.out.println("File not found.");
    }
  }

  public void restoreFile(String name) {
    MyFile file = files.get(name);
    if (file != null && file.isDeleted()) {
      file.setDeleted(false);
      addAccessLog("File restored: " + name);
    } else {
      System.out.println("File not found or not deleted.");
    }
  }

}

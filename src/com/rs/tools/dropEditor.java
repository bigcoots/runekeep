package com.rs.tools;
 
import com.rs.Settings;
import com.rs.cache.Cache;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.npc.Drop;
import com.rs.utils.NPCDrops;
import com.rs.utils.Utils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map.Entry;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
 
/**
 * @author Marvin
 */
public class dropEditor extends javax.swing.JFrame {
 
    private static final long serialVersionUID = 1L;
    private DefaultMutableTreeNode dropsNode = new DefaultMutableTreeNode("Drops");
    private DefaultTreeModel dropsTreeModel = new DefaultTreeModel(dropsNode);
    private NPCDrops loader = new NPCDrops();
    Random random = new Random();
    private JMenuItem dumpSpecificDropMenuItem;
    private JProgressBar progressBar;
    private JPopupMenu tablePopup;
    private JMenuItem testItem;
 
    /**
     * Creates new form DropEditor
     */
    public dropEditor() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }
        try {
            Cache.init();
            NPCDrops.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
 
 
        SortedMap<Integer, ArrayList<Drop>> values = new TreeMap<Integer, ArrayList<Drop>>(new Comparator<Integer>() {
 
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 > o2) {
                    return 1;
                } else if (o1 < o2) {
                    return -1;
                }
                return 0;
            }
        });
 
        values.putAll(loader.getDropArray());
 
        loader.getDropArray().putAll(values);
 
        for (Entry<Integer, ArrayList<Drop>> s : loader.getDropArray().entrySet()) {
            dropsNode.add(new DefaultMutableTreeNode(s.getKey()));
        }
        initComponents();
    }
 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
 
        treeScroll = new javax.swing.JScrollPane();
        dropsTree = new javax.swing.JTree();
        searchField = new javax.swing.JTextField();
        searchIdLabel = new javax.swing.JLabel();
        middleSeparator = new javax.swing.JSeparator();
        dropsPanel = new javax.swing.JPanel();
        dropsForLabel = new javax.swing.JLabel();
        headerSeparator = new javax.swing.JSeparator();
        npcIdLabel = new javax.swing.JLabel();
        tableScroll = new javax.swing.JScrollPane();
        dropTable = new javax.swing.JTable();
        tablePopup = new JPopupMenu();
        testItem = new JMenuItem("Test Rate");
        addNew = new javax.swing.JButton();
        deleteSelected = new javax.swing.JButton();
        repackDrop = new javax.swing.JButton();
        addNewButton = new javax.swing.JButton();
        removeDrop = new javax.swing.JButton();
        fileMenu = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        dumpDropMenuItem = new javax.swing.JMenuItem();
        dumpSpecificDropMenuItem = new javax.swing.JMenuItem();
        progressBar = new JProgressBar(-1, Utils.getNPCDefinitionsSize());
        progressBar.setStringPainted(true);
 
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon("./data/tools/icon.png").getImage());
        setTitle(Settings.SERVER_NAME + " Drop Editor");
 
        dropsTree.setModel(dropsTreeModel);
        dropsTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
 
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                dropsTreeValueChanged(evt);
            }
        });
        treeScroll.setViewportView(dropsTree);
 
        searchField.addActionListener(new java.awt.event.ActionListener() {
 
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchFieldActionPerformed(evt);
            }
        });
 
        searchIdLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        searchIdLabel.setText("Search ID:");
 
        middleSeparator.setOrientation(javax.swing.SwingConstants.VERTICAL);
 
        dropsForLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        dropsForLabel.setText("Drops for NPC ID:");
 
        npcIdLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        npcIdLabel.setText(" ");
 
        dropTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{}));
        testItem.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent arg0) {
                testDropRate(arg0);
 
            }
        });
        tablePopup.add(testItem);
 
        dropTable.addMouseListener(new MouseListener() {
 
            @Override
            public void mouseClicked(MouseEvent arg0) {
                if (arg0.getButton() == MouseEvent.BUTTON3) {
                    int row = dropTable.rowAtPoint(arg0.getPoint());
                    dropTable.changeSelection(row, dropTable.getSelectedColumn(), false, false);
                    tablePopup.show(dropTable, arg0.getX(), arg0.getY());
                }
 
            }
 
            @Override
            public void mouseEntered(MouseEvent arg0) {
                // TODO Auto-generated method stub
            }
 
            @Override
            public void mouseExited(MouseEvent arg0) {
                // TODO Auto-generated method stub
            }
 
            @Override
            public void mousePressed(MouseEvent arg0) {
                // TODO Auto-generated method stub
            }
 
            @Override
            public void mouseReleased(MouseEvent arg0) {
                // TODO Auto-generated method stub
            }
        });
        tableScroll.setViewportView(dropTable);
 
        addNew.setText("Add New Drop");
        addNew.addActionListener(new java.awt.event.ActionListener() {
 
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewActionPerformed(evt);
            }
        });
 
        deleteSelected.setText("Delete Selected Drop");
        deleteSelected.addActionListener(new java.awt.event.ActionListener() {
 
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSelectedActionPerformed(evt);
            }
        });
 
        repackDrop.setText("Repack");
        repackDrop.addActionListener(new java.awt.event.ActionListener() {
 
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                repackDropActionPerformed(evt);
            }
        });
 
        javax.swing.GroupLayout dropsPanelLayout = new javax.swing.GroupLayout(dropsPanel);
        dropsPanel.setLayout(dropsPanelLayout);
        dropsPanelLayout.setHorizontalGroup(
                dropsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dropsPanelLayout.createSequentialGroup().addContainerGap().addGroup(dropsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(headerSeparator, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE).addGroup(javax.swing.GroupLayout.Alignment.LEADING, dropsPanelLayout.createSequentialGroup().addComponent(dropsForLabel).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(npcIdLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)).addComponent(tableScroll, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE).addGroup(javax.swing.GroupLayout.Alignment.LEADING, dropsPanelLayout.createSequentialGroup().addComponent(addNew, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(deleteSelected, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(repackDrop, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))).addContainerGap()));
        dropsPanelLayout.setVerticalGroup(
                dropsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(dropsPanelLayout.createSequentialGroup().addGroup(dropsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(dropsForLabel).addComponent(npcIdLabel)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(headerSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(tableScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(dropsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE).addComponent(addNew).addComponent(deleteSelected).addComponent(repackDrop)).addContainerGap()));
 
        addNewButton.setText("Add New NPC Drop");
        addNewButton.addActionListener(new java.awt.event.ActionListener() {
 
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewButtonActionPerformed(evt);
            }
        });
 
        removeDrop.setText("Remove NPC Drop");
        removeDrop.setToolTipText("Removes the currently Selected NPC Drop");
        removeDrop.addActionListener(new java.awt.event.ActionListener() {
 
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeDropActionPerformed(evt);
            }
        });
 
        jMenu1.setText("File");
 
        dumpDropMenuItem.setText("Dump all monster drops");
        dumpDropMenuItem.addActionListener(new java.awt.event.ActionListener() {
 
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dumpDropMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(dumpDropMenuItem);
        dumpSpecificDropMenuItem.setText("Dump specific monster drop");
        dumpSpecificDropMenuItem.addActionListener(new java.awt.event.ActionListener() {
 
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dumpSpecificDropMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(dumpSpecificDropMenuItem);
 
        fileMenu.add(jMenu1);
 
        setJMenuBar(fileMenu);
 
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false).addComponent(removeDrop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGroup(layout.createSequentialGroup().addComponent(searchIdLabel).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)).addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addComponent(treeScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE).addComponent(addNewButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED).addComponent(middleSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18).addComponent(dropsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(dropsPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(middleSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE))).addGroup(layout.createSequentialGroup().addGap(15, 15, 15).addComponent(treeScroll).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(addNewButton).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(removeDrop).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING).addComponent(searchIdLabel).addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))).addContainerGap()));
 
        pack();
    }// </editor-fold>//GEN-END:initComponents
 
    protected void testDropRate(ActionEvent arg0) {
        ArrayList<Drop> drops = loader.getDropArray().get(Integer.parseInt(dropsTree.getLastSelectedPathComponent().toString()));
        Drop drop = drops.get(dropTable.getSelectedRow());
        String testAmount = JOptionPane.showInputDialog(this, "Enter the amount of times you want to test(0 to test till it drops)");
        int testAmt = 0;
        try {
            testAmt = Integer.parseInt(testAmount);
        } catch (Exception e) {
            testAmt = 0;
        }
        if (testAmt > 0) {
            int successfullDrops = 0;
            for (int i = 0; i < testAmt; i++) {
                int chance = random.nextInt(100);
                if (chance <= drop.getRate()) {
                    successfullDrops++;
                }
            }
            JOptionPane.showMessageDialog(this, "Received the drop " + successfullDrops + " out of " + testAmt + " times");
        } else {
            boolean didntReceive = true;
            int times = 0;
            while (didntReceive) {
                int chance = random.nextInt(100);
                if (chance <= drop.getRate()) {
                    didntReceive = false;
                } else {
                    times++;
                }
            }
            JOptionPane.showMessageDialog(this, "Received the drop after " + times + " times");
        }
 
    }
 
    protected void dumpSpecificDropMenuItemActionPerformed(ActionEvent evt) {
        try {
            dump(Integer.parseInt(JOptionPane.showInputDialog(this, "Enter the NPC ID")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
 
    protected void dumpDropMenuItemActionPerformed(ActionEvent evt) {
        Thread dumpThread = new Thread() {
 
            int index = -1;
 
            public void run() {
                while (index < Utils.getNPCDefinitionsSize()) {
                    try {
                        progressBar.setString(NPCDefinitions.getNPCDefinitions(index).name);
                        dump(index);
                        index++;
                        progressBar.setValue(index);
                        Thread.sleep(1);
                    } catch (Exception e) {
                        System.out.println("Problem with npc id " + index + ".");
                        index++;
                        progressBar.setValue(index);
                    }
                }
                packFile();
                progressBar.setString("");
                progressBar.setValue(-1);
            }
        };
        dumpThread.start();
    }
 
    public void dump(int id) throws Exception {
        NPCDefinitions def = NPCDefinitions.getNPCDefinitions(id);
        System.out.print("Dumping drops for " + def.name + "\n");
        if (def != null && def.name != null && !def.name.equals("") && !def.name.equals("null") && !mapHasNpc(def)) {
            //InputStream instr;
            URL url = new URL("http://runescape.wikia.com/wiki/" + def.name.replace(" ", "_").replace(".", ""));
            URLConnection curl = url.openConnection();
            curl.setDoInput(true);
            if (pageExists(curl)) {
                InputStream instr = curl.getInputStream();
                String s;
                ArrayList<Drop> list = new ArrayList<Drop>();
                ItemDefinitions itemDef = ItemDefinitions.getItemDefinitions(526); //ItemDefinitions.getItemDefinitions(526)
                double rate = 0;
                int minAmount = 1;
                int maxAmount = 1;
                boolean b = true;
                boolean b1 = false;
                boolean b2 = false;
                boolean b3 = false;
                boolean plusOne = false;
                boolean isRare = false;
                if (instr != null) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(instr));
                    while ((s = in.readLine()) != null) {
                        if (s.contains("<td align=\"center\"><a href=\"/wiki/Rare_drop_table\" title=\"Rare drop table\">") && b) {
                            isRare = true;
                            b = false;
                            b1 = true;
                        } else if (s.contains("<td align=\"center\"><span class=\"GEIcon\">") || s.contains("<td align=\"center\"><a href=\"/wiki/Coins\"") && b) {
 
                            itemDef = ItemDefinitions.forName(s.substring(s.indexOf("title=\"") + 7, s.indexOf("\"", s.indexOf("title=\"") + 7)));
                            if (itemDef == null) {
                                try {
                                    itemDef = ItemDefinitions.forName(s.substring(s.indexOf(s.substring(s.indexOf("title=\"") + 7, s.indexOf("\"", s.indexOf("title=\"") + 7))), s.indexOf(" (")).replace("'", "'"));
                                } catch (Exception e) {
                                }
                            }
                            b = false;
                            b1 = true;
                        } else if (b1) { //skip
                            b1 = false;
                            b2 = true;
                        } else if (b2) {
                            if (!isRare) {
                                if (s.contains("(noted)") || s.contains("noted") || s.contains("(noted)")) {
                                    plusOne = true;
                                }
                                if (s.contains("&ndash;")) {
                                    if (!s.contains(",")) {
                                        minAmount = Integer.parseInt(s.substring(9, s.indexOf("&")).replace(" ", ""));
                                    } else {
                                        minAmount = Integer.parseInt(s.substring(9, s.indexOf("&")).replace(" ", "").split(",")[0]);
                                    }
                                    if (s.contains("<i>")) {
                                        maxAmount = Integer.parseInt(s.substring(s.indexOf("&ndash;") + 7, s.indexOf(",", s.indexOf("&ndash;") + 7)).replace(" ", ""));
                                    } else {
                                        try {
                                            maxAmount = Integer.parseInt(s.substring(s.indexOf("&ndash;") + 7).replace(" ", "").split(",")[s.substring(s.indexOf("&ndash;") + 7).replace(" ", "").split(",").length - 1].replace(s.substring(s.indexOf("&ndash;") + 7), ""));
                                        } catch (NumberFormatException d) {
                                        }
                                    }
                                } else if (s.contains(",")) {
 
                                    minAmount = Integer.parseInt(s.substring(9, s.indexOf(",")).replace(" ", "").replace("(noted", ""));
                                    maxAmount = Integer.parseInt(s.substring(9).replace(" ", "").replace("noted", "").replace("(Un)", "").replace("()", "").replace("(Unnoted)", "").replace("(", "").replace("(mostcommon)", "").replace("or", ",").replace("droppedwithdwarfweed)", "").replace("droppedwithavantoe)", "").split(",")[s.substring(9).replace(" ", "").replace("noted", "").replace("(Un)", "").replace("()", "").replace("(", "").replace("(mostcommon)", "").replace("or", ",").replace("droppedwithdwarfweed)", "").replace("droppedwithavantoe)", "").split(",").length - 1]);
                                } else if (s.contains("or")) {
                                    minAmount = Integer.parseInt(s.substring(9, s.indexOf("or")).replace(" ", "").replace("(droppedwithsuperrest", ""));
                                    maxAmount = Integer.parseInt(s.substring(9).replace(" ", "").replace("(Unnoted)", "").replace("(noted)", "").replace("(mostcommon)", "").replace("es)", "").replace("(droppedwithsuperrest", "").split("or")[s.substring(9).replace(" ", "").replace("(noted)", "").replace("(mostcommon)", "").replace("es)", "").replace("(droppedwithsuperrest", "").split("or").length - 1]);
 
 
                                } else if (!s.contains("Unknown") && !s.contains("unknown") && !s.contains("?")) {
                                    maxAmount = minAmount = Integer.parseInt(s.substring(9).equals("") ? "1" : s.substring(9).replace(" ", "").replace("???", "1").replace("<sub>(m)</sub>", "000000").replace("(noted)", "").replace("(Noted)", "").replace("(notnoted)", "").replace("noted", "").replace("(not)", "").replace("(droppedwithsaradominbrews)", ""));
 
                                }
                            }
                            b2 = false;
                            b3 = true;
                        } else if (b3) { //<span style="display:none;">
                            try {
                                rate = getRate(itemDef, Integer.parseInt(s.substring(s.indexOf("<span style=\"display:none;\">") + 28, s.indexOf("</span>"))));
                            } catch (Exception e) {
                                rate = 50.0;
                            }
                            if (rate == 0.0) {
                                rate = getPricedPercent(itemDef);
                            }
                            if (!isRare) {
                                list.add(Drop.create(itemDef == null ? 526 : itemDef.id + (plusOne ? 1 : 0), rate, minAmount, maxAmount, isRare));
                            } else {
                                list.add(Drop.create(-1, 100.0, 1, 1, isRare));
                            }
                            b = true;
                            b1 = false;
                            b2 = false;
                            b3 = false;
                            isRare = false;
                            plusOne = false;
                        }
 
                    }
                    System.out.println("Dumped drops for " + def.name);
                    if (list.size() > 0) {
                        Drop[] finallist = (Drop[]) list.toArray();
                        loader.getDropMap().put(def.npcId, finallist);
                        dropsNode.add(new DefaultMutableTreeNode(def.npcId));
                        dropsTreeModel.reload(dropsNode);
                        // Logger.getLogger(this.getName(), "Dumped drops for " + def.name);
                    }
                    in.close();
                    instr.close();
                }
            }
        }
    }
 
    private boolean pageExists(URLConnection curl) {
        try {
            if (curl.getInputStream() != null) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
 
    public boolean mapHasNpc(NPCDefinitions def) {
        try {
            for (Entry<Integer, ArrayList<Drop>> e : loader.getDropArray().entrySet()) {
                if (def.id == e.getKey()) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
 
    private double getRate(ItemDefinitions def, int i) {
        if (def.getValue() > 10000000) {
            i = 5;
        }
        switch (i) {
            case 1:
                return 100;
            case 2:
                return 75 + (int) (Math.random() * ((99 - 75) + 1));
            case 3:
                return 55 + (int) (Math.random() * ((74 - 55) + 1));
            case 4:
                return 25 + (int) (Math.random() * ((54 - 25) + 1));
            case 5:
                return random.nextInt(10);
            case 7:
                return getPricedPercent(def);
        }
        return 0;
    }
 
    private double getPricedPercent(ItemDefinitions def) {
        if (def.getValue() > 0 && def.getValue() < 1000) {
            return 75 + (int) (Math.random() * ((99 - 75) + 1));
        } else if (def.getValue() > 1000 && def.getValue() < 100000) {
            return 55 + (int) (Math.random() * ((74 - 55) + 1));
        } else if (def.getValue() > 100000 && def.getValue() < 1000000) {
            return 25 + (int) (Math.random() * ((54 - 25) + 1));
        } else if (def.getValue() > 100000 && def.getValue() < 1000000) {
            return random.nextInt(10);
        } else {
            return 1;
        }
    }
 
    public DefaultMutableTreeNode searchNode(String nodeStr) {
        DefaultMutableTreeNode nodeToSearch = null;
        Enumeration<?> e = dropsNode.breadthFirstEnumeration();
        while (e.hasMoreElements()) {
            nodeToSearch = (DefaultMutableTreeNode) e.nextElement();
            if (nodeStr.equals(nodeToSearch.getUserObject().toString())) {
                return nodeToSearch;
            }
        }
        return null;
    }
 
    private void searchFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchFieldActionPerformed
        try {
            DefaultMutableTreeNode n = searchNode(searchField.getText());
            TreePath path = new TreePath(dropsTreeModel.getPathToRoot(n));
            dropsTree.scrollPathToVisible(path);
            dropsTree.setSelectionPath(path);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Could not find the item.");
        }
    }//GEN-LAST:event_searchFieldActionPerformed
 
    private void dropsTreeValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_dropsTreeValueChanged
        if (dropsTree.getLastSelectedPathComponent() != null && !"Drops".equals(dropsTree.getLastSelectedPathComponent().toString())) {
            loadDrop(Integer.parseInt(dropsTree.getLastSelectedPathComponent().toString()));
        }
    }//GEN-LAST:event_dropsTreeValueChanged
 
    private void deleteSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSelectedActionPerformed
        try {
            int npcID = Integer.parseInt(dropsTree.getLastSelectedPathComponent().toString());
            ArrayList<Drop> drops = loader.getDropArray().get(npcID);
            drops.remove(dropTable.getSelectedRow());
            Drop[] d = new Drop[drops.size()];
            drops.toArray(d);
            loader.getDropMap().put(npcID, d);
 
            loadDrop(Integer.parseInt(dropsTree.getLastSelectedPathComponent().toString()));
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Please select a drop first!");
        }
    }//GEN-LAST:event_deleteSelectedActionPerformed
 
    private void addNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewActionPerformed
        // try {
        int npcid = Integer.parseInt(dropsTree.getLastSelectedPathComponent().toString());
        // ArrayList<Drop> drops = loader.getDropArray().get(npcid);
        loader.insertDrop(npcid, Drop.create(Integer.parseInt(JOptionPane.showInputDialog("Please enter the item ID for this drop.")), (int) 100.0, 1, 1, false));
 
 
        loadDrop(npcid);
        // } catch (NullPointerException e) {
 
        //JOptionPane.showMessageDialog(this, "Please select a drop first!");
        //}
    }//GEN-LAST:event_addNewActionPerformed
 
    private void addNewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewButtonActionPerformed
        String id = JOptionPane.showInputDialog("Please enter the NPC ID for this drop.");
        if (id == null) {
            id = "0";
        }
        loader.getDropArray().put(Integer.parseInt(id), new ArrayList<Drop>());
        packFile();
    }//GEN-LAST:event_addNewButtonActionPerformed
 
    private void removeDropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeDropActionPerformed
        try {
            if (!dropsTree.getLastSelectedPathComponent().toString().equals("")) {
                loader.getDropMap().remove(Integer.parseInt(dropsTree.getLastSelectedPathComponent().toString()));
                packFile();
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Please select a drop first!");
        }
    }//GEN-LAST:event_removeDropActionPerformed
 
    private void repackDropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_repackDropActionPerformed
        packFile();
    }//GEN-LAST:event_repackDropActionPerformed
 
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
 
            public void run() {
                new dropEditor().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addNew;
    private javax.swing.JButton addNewButton;
    private javax.swing.JButton deleteSelected;
    private javax.swing.JTable dropTable;
    private javax.swing.JLabel dropsForLabel;
    private javax.swing.JPanel dropsPanel;
    private javax.swing.JTree dropsTree;
    private javax.swing.JMenuItem dumpDropMenuItem;
    private javax.swing.JMenuBar fileMenu;
    private javax.swing.JSeparator headerSeparator;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JSeparator middleSeparator;
    private javax.swing.JLabel npcIdLabel;
    private javax.swing.JButton removeDrop;
    private javax.swing.JButton repackDrop;
    private javax.swing.JTextField searchField;
    private javax.swing.JLabel searchIdLabel;
    private javax.swing.JScrollPane tableScroll;
    private javax.swing.JScrollPane treeScroll;
    // End of variables declaration//GEN-END:variables
 
    private void loadDrop(final int npcId) {
        npcIdLabel.setText(Integer.toString(npcId) + " " + NPCDefinitions.getNPCDefinitions(npcId).name);
        ArrayList<Drop> drops = loader.getDropArray().get(npcId);
        DefaultTableModel model = new DefaultTableModel();
        model.addTableModelListener(new TableModelListener() {
 
            public void tableChanged(TableModelEvent e) {
                try {
                    if (e.getColumn() != -1) {
                        if (dropTable.getValueAt(dropTable.getSelectedRow(), 0).equals("Rare")) {
                            loader.getDropArray().get(npcId).get(dropTable.getSelectedRow()).setItemId((short) -1);
                            loader.getDropArray().get(npcId).get(dropTable.getSelectedRow()).setRate(-1);
                            //String amount = (String) dropTable.getValueAt(dropTable.getSelectedRow(), 2);
                            loader.getDropArray().get(npcId).get(dropTable.getSelectedRow()).setMinAmount(1);
                            loader.getDropArray().get(npcId).get(dropTable.getSelectedRow()).setMaxAmount(1);
 
                        } else {
                            loader.getDropArray().get(npcId).get(dropTable.getSelectedRow()).setItemId(Short.parseShort((String) dropTable.getValueAt(dropTable.getSelectedRow(), 0).toString()));
                            loader.getDropArray().get(npcId).get(dropTable.getSelectedRow()).setRate(Double.parseDouble((String) dropTable.getValueAt(dropTable.getSelectedRow(), 1).toString()));
                            String amount = (String) dropTable.getValueAt(dropTable.getSelectedRow(), 2);
                            if (amount.contains("-")) {
                                loader.getDropArray().get(npcId).get(dropTable.getSelectedRow()).setMinAmount(Integer.parseInt(amount.substring(0, amount.indexOf("-"))));
                                loader.getDropArray().get(npcId).get(dropTable.getSelectedRow()).setMaxAmount(Integer.parseInt(amount.substring(amount.indexOf("-") + 1)));
                            } else {
                                loader.getDropArray().get(npcId).get(dropTable.getSelectedRow()).setMinAmount(Integer.parseInt(amount));
                                loader.getDropArray().get(npcId).get(dropTable.getSelectedRow()).setMaxAmount(Integer.parseInt(amount));
 
                            }
                        }
                    }
                } catch (Exception f) {
                    f.printStackTrace();
                }
            }
        });
        model.addColumn("Item ID");
        model.addColumn("Percent Chance");
        model.addColumn("Amount");
        for (Drop d : drops) {
            if (d == null) {
                continue;
            }
            if (!d.isFromRareTable()) {
                model.addRow(new Object[]{(d.getItemId()), Double.toString(d.getRate()), Integer.toString(d.getMinAmount()) + (d.getMinAmount() == d.getMaxAmount() || d.getMaxAmount() == 0 ? "" : "-" + d.getMaxAmount())});
            } else {
                model.addRow(new Object[]{"Rare", "100.0", "1"});
            }
        }
        dropTable.setModel(model);
    }
 
    private void packFile() {
        try { // //drops.bin
            RandomAccessFile raf = new RandomAccessFile("data/npcs/packedDrops.d", "rw");
            raf.writeShort(loader.getDropMap().size());
            for (Entry<Integer, ArrayList<Drop>> e : loader.getDropArray().entrySet()) {
                raf.writeShort(e.getKey());
                raf.writeShort(e.getValue().size());
                for (Drop d : e.getValue()) {
 
                    raf.writeByte(d.isFromRareTable() ? 1 : 0);
                    if (!d.isFromRareTable()) {
                        int itemID = d.getItemId();
                        if (ItemDefinitions.getItemDefinitions(itemID).getName().equals("Coins")) {
                            itemID = 995;
                        }
                        raf.writeShort(itemID);
                        raf.writeDouble(d.getRate());
                        if (d.getMinAmount() > d.getMaxAmount()) {
                            int min = d.getMinAmount();
                            d.setMinAmount(d.getMaxAmount());
                            d.setMaxAmount(min);
                        }
                        raf.writeInt(d.getMinAmount());
                        raf.writeInt(d.getMaxAmount());
                    }
                }
            }
            raf.close();
            Logger.getLogger(dropEditor.class.getName()).log(Level.INFO, "Repacked the drops.");
        } catch (IOException ex) {
            Logger.getLogger(dropEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        reload();
    }
 
    private void reload() {
        dropsNode.removeAllChildren();
        //loader = new NPCDropLoader();
        //loader.load();
        for (Entry<Integer, ArrayList<Drop>> s : loader.getDropArray().entrySet()) {
            dropsNode.add(new DefaultMutableTreeNode(s.getKey()));
        }
        dropsTreeModel.reload(dropsNode);
    }
}
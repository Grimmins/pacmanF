package model.Items;

public class Inventory {
    private static final int size = 4;
    private Item[] content = new Item[size];
    private int freeSlots;

    public Inventory(){
        this.freeSlots = size;
    }

    public void add(Item item){
        for(int i = 0; i < size; i++){
            if(content[i] == null){
                content[i] = item;
                break;
            }
        }
        this.freeSlots--;
    }

    public void remove(int index){
        content[index] = null;
        this.freeSlots++;
    }

    public boolean isFull(){
        return this.freeSlots == 0;
    }

    public String toString(){
        String s = "";
        for(int i = 0; i < size; i++){
            if(content[i] == null){
                s += "| " + i + " : None |";
            } else {
                s += "| " + i + " : " + content[i].toString() + " |";
            }
        }
        return s;
    }

    public void useItem(int index){
        if(index < size && this.content[index] != null){
            this.content[index].setActive(true);
        }
    }

    public Item getNth(int index){
        if(index < size && this.content[index] != null){
            return this.content[index];
        } else {
            return new Item();
        }
    }
}
package isn.cards;

import java.io.File;

public class Card {
	
	public enum Type{
		SPADE("Pique"),
		HEART("Coeur"),
		DIAMONDS("Carreau"),
		CLUBS("Trefle");
		
		String name;
		
		private Type(String name) {
			this.name = name;
		}
		
		public static Type getType(int id) throws Exception{
			for(Type t:values()){
				if(t.ordinal()==id){
					return t;
				}
			}
			throw new Exception("Id out of range (0-3)");
		}
		
		public File getImg(){
			return new File("./" + name.toLowerCase() + "png");
		}
	}
	
	private int number;
	private Type type;
	
	public Card(int number, Type type) throws Exception {
		if(number<0 || number>12){
			throw new Exception("Id out of range (0-12)");
		}
		
		this.number = number;
		this.type = type;
	}
	
//	public Card(Random rand, ArrayList<Card> whereToAdd) throws Exception{
//		if(whereToAdd.isEmpty()){
//			whereToAdd.add(new Card(rand.nextInt(13), Type.getType(rand.nextInt(4))));
//		}else{
//			boolean searchC = true;
//			while(searchC){
//				searchC = false;
//				Card toAdd = new Card(rand.nextInt(13), Type.getType(rand.nextInt(4)));
//				
//				Iterator<Card> it = whereToAdd.iterator();
//				while(it.hasNext()){
//					if(it.next().equals(toAdd)){
//						searchC = true;
//						break;
//					}
//				}
//				
//				if(!searchC){
//					whereToAdd.add(toAdd);
//				}
//			}
//		}
//	}
	
	public int getNumber() {
		return number;
	}
	
	public Type getType() {
		return type;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Card && ((Card)obj).number==number && ((Card)obj).type.ordinal()==type.ordinal();
	}

	public File getImg() {
		return new File("./" + number + ".png");
	}
	
}
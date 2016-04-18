package isn.cards;

//Classe qui est une carte
public class Card {
	
	/**
	 * Le type de la carte
	 *
	 */
	public enum Type{
		SPADE("Pique"),
		HEART("Coeur"),
		DIAMONDS("Carreau"),
		CLUBS("Trefle");
		
		/**
		 * Nom de la carte
		 */
		String name;
		
		/**
		 * 
		 * @param name
		 * Constructeur de type
		 */
		private Type(String name) {
			this.name = name;
		}
		
		/**
		 * 
		 * @param id
		 * @return le type en fonction de l'id
		 * @throws Exception
		 */
		public static Type getType(int id) throws Exception{
			for(Type t:values()){
				if(t.ordinal()==id){
					return t;
				}
			}
			throw new Exception("Id out of range (0-3)");
		}
		
		//toString() retourne name
		@Override
		public String toString() {
			return name;
		}
	}
	
	/**
	 * Le nombre de la carte;0 est l'as;...;10 le valet;11 la dame;12 le roi
	 */
	private int number;
	/**
	 * Le type de carte
	 */
	private Type type;
	
	/**
	 * @param number
	 * @param type
	 * @throws Exception
	 * Cree une carte a' partir du nombre et du type
	 */
	public Card(int number, Type type) throws Exception {
		if(number<0 || number>12){
			throw new Exception("Id out of range (0-12)");
		}
		
		this.number = number;
		this.type = type;
	}
	
	/**
	 * 
	 * @return le nombre de la carte
	 */
	public int getNumber() {
		return number;
	}
	
	/**
	 * 
	 * @return le type de la carte
	 */
	public Type getType() {
		return type;
	}
	
	/**
	 * @return Est-ce que les deux cartes sont egales
	 */
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Card && ((Card)obj).number==number && ((Card)obj).type.ordinal()==type.ordinal();
	}
	
	/**
	 * 
	 * @return une copy ne pointant pas au même objet
	 * @throws Exception
	 */
	public Card copy() throws Exception {
		return new Card(getNumber(), getType());
	}
	
}
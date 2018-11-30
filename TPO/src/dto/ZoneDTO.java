package dto;

public class ZoneDTO {

	private String name;
	private int zoneId;
	
	public String getName() {
		return name;
	}
	public int getZoneId() {
		return zoneId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}
	@Override
	public String toString() {
		return name;
	}
	
	public boolean equals(Object other){
	    boolean result;
	    
	    if((other == null) || (getClass() != other.getClass())){
	        result = false;
	    } else {
	        ZoneDTO otherZoneDTO = (ZoneDTO)other;
	        result = (name.equals(otherZoneDTO.getName()));
	    }
	    
	    return result;
	}
	
	
}

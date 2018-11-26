package test;

import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import controller.Controller;
import dto.InvoiceDTO;
import dto.ProductDTO;
import dto.UserDTO;
import dto.ZoneDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClaimException;
import exceptions.InvalidClientException;
import exceptions.InvalidInvoiceException;
import exceptions.InvalidInvoiceItemException;
import exceptions.InvalidProductException;
import exceptions.InvalidProductItemException;
import exceptions.InvalidRoleException;
import exceptions.InvalidTransitionException;
import exceptions.InvalidUserException;
import exceptions.InvalidZoneException;

public class App {
	
	public static void main(String[] args) throws ConnectionException, AccessException, InvalidClientException, InvalidZoneException, InvalidInvoiceException, InvalidRoleException, InvalidUserException, InvalidClaimException, InvalidProductException, InvalidInvoiceItemException, InvalidProductItemException, InvalidTransitionException, SQLException {
		Controller c = Controller.getInstance();
// test addClient
//		List<String> calles = new LinkedList<>();
//		List<ZoneDTO> zonas = c.getAllZones();
		
//		List<String> nombres = new LinkedList<>();
//		nombres.add("Ana");
//		nombres.add("Laura");
//		nombres.add("Martin");
//		nombres.add("Matias");
//		nombres.add("Juan");
//		nombres.add("Maria");
//		nombres.add("Ernesto");
//		nombres.add("Gonzalo");
//		nombres.add("Estefania");
//		nombres.add("Ezequiel");
//		nombres.add("Alejandro");
//		nombres.add("Jimena");
//		nombres.add("Florencia");
//		nombres.add("Juan Pablo");
//		nombres.add("Gustavo");
//		
//		calles.add("Lima");
//		calles.add("9 de Julio");
//		calles.add("Uruguay");
//		calles.add("Paraguay");
//		calles.add("25 de Mayo");
//		calles.add("Sucre");
//		calles.add("Echeverria");
//		calles.add("Zapiola");
//		calles.add("Ciudad de la Paz");
//		calles.add("Demaria");
//		calles.add("Amador");
//		calles.add("España");
//		calles.add("Jose Maria Paz");
//		calles.add("Salta");
//		calles.add("Chile");
//		
//		for (int i = 0; i < 200; i++) {
//			ClientDTO dtoAddClient = new ClientDTO();
//			int cuitUno = new Random().nextInt(89) + 10;
//			int cuitDos = new Random().nextInt(89999999) + 10000000;
//			int cuitTres = new Random().nextInt(8) + 1;
//			dtoAddClient.setCuit(cuitUno + "-" + cuitDos + "-" + cuitTres);
//			dtoAddClient.setName(nombres.get(new Random().nextInt(14)));
//			dtoAddClient.setAddress(calles.get(new Random().nextInt(14)) + " " + new Random().nextInt(5000));
//			dtoAddClient.setPhoneNumber((new Random().nextInt(8999) + 1000) + "-" + (new Random().nextInt(8999) + 1000));
//			dtoAddClient.setEmail("hola@hola.com");
//			dtoAddClient.setZone(zonas.get((new Random().nextInt(45))).getName());
//			c.addClient(dtoAddClient);
//			
//		}
//		
// test modifyClient
//		ClientDTO dtoModifyClient = new ClientDTO();
//		dtoModifyClient.setId(6);
//		dtoModifyClient.setCuit("33-33333333-3");
//		dtoModifyClient.setName("Jimena");
//		dtoModifyClient.setAddress("Lima 123");
//		dtoModifyClient.setPhoneNumber("1234-4234");
//		dtoModifyClient.setEmail("chau@chau.com");
//		dtoModifyClient.setZone("Saavedra");
//		c.modifyClient(dtoModifyClient);
		
// test removeClient
//		ClientDTO dtoRemoveClient = new ClientDTO();
//		dtoRemoveClient.setId(4);
//		c.removeClient(dtoRemoveClient);
		
// test addUser
//		List<String> nombres = new LinkedList<>();
//		nombres.add("Ana");
//		nombres.add("Laura");
//		nombres.add("Martin");
//		nombres.add("Matias");
//		nombres.add("Juan");
//		nombres.add("Maria");
//		nombres.add("Ernesto");
//		nombres.add("Gonzalo");
//		nombres.add("Estefania");
//		nombres.add("Ezequiel");
//		nombres.add("Alejandro");
//		nombres.add("Jimena");
//		nombres.add("Florencia");
//		nombres.add("Juan Pablo");
//		nombres.add("Gustavo");
//		
//		UserDTO dtoAddUser = new UserDTO();
//		Roles[] roles = Roles.values();
//		
//		for (int i = 0; i < 30; i++) {
//			dtoAddUser.setName(nombres.get(new Random().nextInt(14)));
//			dtoAddUser.setPrincipalRole(roles[new Random().nextInt(6)].toString());
//			dtoAddUser.setUserName(dtoAddUser.getName() + new Random().nextInt(100));
//			dtoAddUser.setPassword("123");
//			System.out.println(dtoAddUser.getName());
//			System.out.println(dtoAddUser.getPrincipalRole());
//			System.out.println(dtoAddUser.getUserName());
//			System.out.println(dtoAddUser.getPassword());
//			c.addUser(dtoAddUser);
//			
//		}
		

// test modifyUser
//		UserDTO dtoUser = new UserDTO();
//		dtoUser.setUserId(3);
//		dtoUser.setName("Juan Pedro");
//		dtoUser.setPrincipalRole(Roles.CALL_CENTER_RESPONSABLE.name());
//		c.modifyUser(dtoUser);
		
// test removeUser
//		UserDTO dtoUser = new UserDTO();
//		dtoUser.setUserId(5);
//		c.removeUser(dtoUser);

// test addProduct	
//		List<String> nombres = new LinkedList<>();
//		nombres.add("Arandano");
//		nombres.add("Frambuesa");
//		nombres.add("Fresa");
//		nombres.add("Zarzamora");
//		nombres.add("Limon");
//		nombres.add("Mandarina");
//		nombres.add("Naranja");
//		nombres.add("Pomelo");
//		nombres.add("Melon");
//		nombres.add("Sandia");
//		nombres.add("Aguacate");
//		nombres.add("Cereza");
//		nombres.add("Ciruela");
//		nombres.add("Kaki");
//		
//		List<String> pais = new LinkedList<>();
//		pais.add("Uruguay");
//		pais.add("Argentina");
//		pais.add("Chile");
//		pais.add("Paraguay");
//		pais.add("Bolivia");
//		pais.add("Brasil");
//		pais.add("Ecuador");
//		pais.add("Venezuela");
//		pais.add("Colombia");
//		pais.add("Alemania");
//		pais.add("Rusia");
//		
//		ProductDTO dtoProduct = new ProductDTO();
//		for (int i = 0; i < 100; i++) {
//			dtoProduct.setTitle(nombres.get(new Random().nextInt(14)));
//			dtoProduct.setDescription("Industria " + pais.get(new Random().nextInt(11)));
//			dtoProduct.setPrice(new Random().nextInt(300));			
//			c.addProduct(dtoProduct);
//		}
		
// test modifyProduct
//		ProductDTO dtoProduct = new ProductDTO();
//		dtoProduct.setProductId(1);
//		dtoProduct.setTitle("Kiwi");
//		dtoProduct.setDescription("Importadas de Colombia");
//		dtoProduct.setPrice(12);
//		c.modifyProduct(dtoProduct);

// test removeProduct
//		ProductDTO dtoProduct = new ProductDTO();
//		dtoProduct.setProductId(1);
//		c.removeProduct(dtoProduct);
		
// test addRole
//		RoleDTO dtoRole = new RoleDTO();
//		dtoRole.setRole(Roles.ADMINISTRATOR.name());
//		dtoRole.setUserId(3);
//		c.addRole(dtoRole);

// test removeRole
//		RoleDTO dtoRole = new RoleDTO();
//		dtoRole.setUserId(3);
//		c.removeRole(dtoRole);
		
// test addInvoice
//		InvoiceDTO dtoInvoice = new InvoiceDTO();
//		for (int i = 0; i < 20; i++) {
//			int canProduct = new Random().nextInt(10);
//			
//			for (int j = 0; j < canProduct; j++) {
//				ProductDTO product = new ProductDTO();
//				product.setProductId(new Random().nextInt(20) + 2);
//				dtoInvoice.addProductItemDTO(product, new Random().nextInt(100));
//			}
//			dtoInvoice.setClientId(new Random().nextInt(190));
//			dtoInvoice.setDate(new Date());
//			c.addInvoice(dtoInvoice);
//			System.out.println(i);
//		
//		}
//		
// test removeInvoice
//		InvoiceDTO dtoInvoice = new InvoiceDTO();
//		dtoInvoice.setInvoiceId(1);
//		c.removeInvoice(dtoInvoice);
		
// test addWrongInvoicingClaim
//		WrongInvoicingClaimDTO dtoWrong = new WrongInvoicingClaimDTO();
//		dtoWrong.setClientId(3);
//		dtoWrong.setDescription("No me gusta nada");
//		dtoWrong.addInvoiceItemDTO(2, "No me gusta");
//		c.addWrongInvoicingClaim(dtoWrong);
		
// test treatClaim
//		TransitionDTO dtoTransition = new TransitionDTO();
//		dtoTransition.setResponsableId(1);
//		dtoTransition.setNewState(State.SOLVED.name());
//		dtoTransition.setDescription("Estoy haciendo una prueba");
//		dtoTransition.setClaimId(7);
//		c.treatClaim(dtoTransition);
//		
// test getClaimState
//		System.out.println(c.getClaimState(3));

// test getInvoicesByClient
//		for (InvoiceDTO i : c.getInvoicesByClient(2)) {
//			System.out.println(i.getClientId());
//		}
	
// test userExists
//		UserDTO dto = new UserDTO();
//		dto.setUserId(12);
//		System.out.println(c.userExists(dto));
	
// test getInvoiceProducts
//		for (ProductDTO p : c.getInvoiceProducts(2)) {
//			System.out.println(p.getTitle());
//			System.out.println(p.getProductId());
//			System.out.println(p.getDescription());
//			System.out.println("");
//		}

// test getClientById
//		System.out.println(c.getClientById(9));
		
// test userExists
//		UserDTO dto1 = new UserDTO();
//		dto1.setUserId(1);
//		System.out.println(c.userExists(dto1));
//		UserDTO dto2 = new UserDTO();
//		dto2.setUserId(9);
//		System.out.println(c.userExists(dto2));
	
// test getClaimsFromClient
//		for (ClaimDTO claim : c.getClaimsFromClient(2)) {
//			System.out.println(claim.getDescription());
//		}
		
// test getUserById
//		UserDTO aux = c.getUserById(6);
//		if (aux != null) {
//			System.out.println(aux.getName());
//		} else {
//			System.out.println("Null return");
//		}
		
// test addMoreQuantityClaim
//		MoreQuantityClaimDTO dto = new MoreQuantityClaimDTO();
//		dto.setClientId(2);
//		dto.addProductItemDTO(3, 4);
//		dto.addProductItemDTO(2, 5);
//		dto.setClaimType(ClaimType.MORE_QUANTITY.name());
//		dto.setInvoiceId(3);
//		dto.setDescription("Necesito mas");
//		c.addMoreQuantityClaim(dto);
		
// test getUserByUsernameAndPassword
//		UserDTO dto = new UserDTO();
//		dto.setUserName("Matias3");
//		dto.setPassword("123");
//		UserDTO aux = c.getUserByUsernameAndPassword(dto);
//		System.out.println((aux == null) ? "No existe" : aux.getName());
		
// test addCompositeClaim
//		CompositeClaimDTO aux = new CompositeClaimDTO();
//		aux.addIndividualClaimId(8);
//		aux.addIndividualClaimId(10);
//		aux.setClientId(3);
//		aux.setDescription("Test CompositeClaim");
//		c.addCompositeClaim(aux);
		
// test IncompatibleZoneClaim
//		IncompatibleZoneClaimDTO dto = new IncompatibleZoneClaimDTO();
//		dto.setClientId(3);
//		dto.setDescription("Hola");
//		c.addIncompatibleZoneClaim(dto);

// test rankingPer Month
//		for (ClaimQuantityPerMonthDTO d : c.getRankingClaimsPerMonth()) {
//			System.out.println("Mes " + d.getMonth() + " Cantidad: " + d.getQuantity());
//		}
		
// test rankingOfClient
//		for (ClientDTO d : c.getRankingClientsOfClaims()) {
//			System.out.println(d.getName() + " " + d.getRanking() + " " + d.getClaimsCount());
//		}
		
// test getAllWrongInvoicingClaimsDTOFromClient	
//		List<WrongInvoicingClaimDTO> claims = c.getAllWrongInvoicingClaimsDTOFromClient(3);
//		System.out.println();
		
// test getAumountOfClaims
//		ClaimsPerCategoryDTO aux = c.getAmountOfClaimsPerCategory();
//		

		
// test addZone
//		c.addZone("Almagro");
//		c.addZone("Balvanera");
//		c.addZone("Barracas");
//		c.addZone("Belgrano");
//		c.addZone("Boedo");
//		c.addZone("Caballito");
//		c.addZone("Chacarita");
//		c.addZone("Coghlan");
//		c.addZone("Colegiales");
//		c.addZone("Constitucion");
//		c.addZone("Flores");
//		c.addZone("Floresta");
//		c.addZone("La Boca");
//		c.addZone("La Paternal");
//		c.addZone("Liniers");
//		c.addZone("Mataderos");
//		c.addZone("Monte Castro");
//		c.addZone("Montserrat");
//		c.addZone("Nueva Pompeya");
//		c.addZone("Nuñez");
//		c.addZone("Palermo");
//		c.addZone("Parque Avellaneda");
//		c.addZone("Parque Chacabuco");
//		c.addZone("Parque Chas");
//		c.addZone("Parque Patricios");
//		c.addZone("Recoleta");
//		c.addZone("Retiro");
//		c.addZone("San Cristobal");
//		c.addZone("San Nicolas");
//		c.addZone("San Telmo");
//		c.addZone("Versalles");
//		c.addZone("Villa Crespo");
//		c.addZone("Villa Devoto");
//		c.addZone("Villa General Mitre");
//		c.addZone("Villa Lugano");
//		c.addZone("Villa Luro");
//		c.addZone("Villa Ortuzar");
//		c.addZone("Villa Pueyrredon");
//		c.addZone("Villa Real");
//		c.addZone("Villa Riachuelo");
//		c.addZone("Villa Santa Rita");
//		c.addZone("Villa Soldati");
//		c.addZone("Villa Urquiza");
//		c.addZone("Villa del Parque");
//		c.addZone("Velez Sarsfield");
		
// test getUserRanking
		
		for (UserDTO u : c.getRankingOfUsers()) {
			System.out.println(u.getName());
			System.out.println(u.getRanking());
			System.out.println(u.getAvgReponseTime());
		}
		System.out.println("END");
	}
}

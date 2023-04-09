package fr.nacvs.ied_mediator.manual;

import java.time.LocalDate;

import org.apache.commons.text.StringEscapeUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.nacvs.ied_mediator.sources.film_summary.XmlHelper;
import fr.nacvs.ied_mediator.util.DateUtils;

public class XPathTest {

	public static void main(String[] args) {
		XmlHelper helper = new XmlHelper();

		System.out.println("Pirates of the Caribbean: Dead Man's Chest".replaceAll(":([^\\s])", ": $1"));
		
//		testErrorResponse(helper);
		testValidResponse(helper);
//		testDetailsResponse(helper);
	}

	private static void testErrorResponse(XmlHelper helper) {
		Document xml = helper.loadXml("<root response=\"False\"><error>Movie not found!</error></root>");
		// Check response value
		System.out.println(helper.anyMatch(xml, "/root[@response = 'True']"));
		System.out.println(Boolean.parseBoolean(helper.findString(xml, "/root/@response")));
		System.out.println(helper.findBoolean(xml, "/root/@response"));
		// Get error message
		System.out.println(helper.findString(xml, "/root/error"));
	}

	private static void testValidResponse(XmlHelper helper) {
		// Find result count
		Document response = helper.loadXml("<root totalResults=\"20\" response=\"True\">\r\n" + 
				"<result title=\"Pirates of the Caribbean: The Curse of the Black Pearl\" year=\"2003\" imdbID=\"tt0325980\" type=\"movie\" poster=\"https://m.media-amazon.com/images/M/MV5BNGYyZGM5MGMtYTY2Ni00M2Y1LWIzNjQtYWUzM2VlNGVhMDNhXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg\"/>\r\n" + 
				"<result title=\"Pirates of the Caribbean: Dead Man's Chest\" year=\"2006\" imdbID=\"tt0383574\" type=\"movie\" poster=\"https://m.media-amazon.com/images/M/MV5BMTcwODc1MTMxM15BMl5BanBnXkFtZTYwMDg1NzY3._V1_SX300.jpg\"/>\r\n" + 
				"<result title=\"Pirates of the Caribbean: At World's End\" year=\"2007\" imdbID=\"tt0449088\" type=\"movie\" poster=\"https://m.media-amazon.com/images/M/MV5BMjIyNjkxNzEyMl5BMl5BanBnXkFtZTYwMjc3MDE3._V1_SX300.jpg\"/>\r\n" + 
				"<result title=\"Pirates of the Caribbean: On Stranger Tides\" year=\"2011\" imdbID=\"tt1298650\" type=\"movie\" poster=\"https://m.media-amazon.com/images/M/MV5BMjE5MjkwODI3Nl5BMl5BanBnXkFtZTcwNjcwMDk4NA@@._V1_SX300.jpg\"/>\r\n" + 
				"<result title=\"Pirates of the Caribbean: Dead Men Tell No Tales\" year=\"2017\" imdbID=\"tt1790809\" type=\"movie\" poster=\"https://m.media-amazon.com/images/M/MV5BMTYyMTcxNzc5M15BMl5BanBnXkFtZTgwOTg2ODE2MTI@._V1_SX300.jpg\"/>\r\n" + 
				"<result title=\"Pirates of the Caribbean: Tales of the Code: Wedlocked\" year=\"2011\" imdbID=\"tt2092452\" type=\"movie\" poster=\"https://m.media-amazon.com/images/M/MV5BZGFiZTQ0MDctM2ViMS00MGEwLWIxNzgtYWUzZDM4N2NiMmQyXkEyXkFqcGdeQXVyNTE1NjY5Mg@@._V1_SX300.jpg\"/>\r\n" + 
				"<result title=\"An Epic at Sea: The Making of 'Pirates of the Caribbean: The Curse of the Black Pearl'\" year=\"2003\" imdbID=\"tt0395141\" type=\"movie\" poster=\"https://m.media-amazon.com/images/M/MV5BMTM2OTEwNTExNF5BMl5BanBnXkFtZTcwNjM3OTMyMQ@@._V1_SX300.jpg\"/>\r\n" + 
				"<result title=\"Pirates of the Caribbean: Secrets of Dead Man's Chest\" year=\"2006\" imdbID=\"tt0857391\" type=\"movie\" poster=\"https://m.media-amazon.com/images/M/MV5BNWY5NDEwMTctZmZhMS00MjBhLTkxM2MtZGM3ZWFiN2MxMTk0XkEyXkFqcGdeQXVyNTc0NjY1ODk@._V1_SX300.jpg\"/>\r\n" + 
				"<result title=\"A Journey Behind the Scenes of 'Pirates of the Caribbean: At World's End'\" year=\"2007\" imdbID=\"tt1053871\" type=\"movie\"/>\r\n" + 
				"<result title=\"Pirates of the Caribbean: On Stranger Tides 35mm 3D Special\" year=\"2011\" imdbID=\"tt1937286\" type=\"movie\" poster=\"https://m.media-amazon.com/images/M/MV5BMjRjYTY4YjUtMjUzNC00YjNiLTkyYTMtODFhOThmMTNjODliXkEyXkFqcGdeQXVyODQ5NTg5MTk@._V1_SX300.jpg\"/>\r\n" + 
				"</root>");

		// Find total results count
		double totalResults = helper.findDouble(response, "/root/@totalResults");
		System.out.println("Total results = " + totalResults);
		// Find all nodes with precise title
		String title = "Pirates of the Caribbean:On Stranger Tides";
		String titleEscaped = StringEscapeUtils.escapeHtml4(title.replaceAll(":([^\\s])", ": $1"));
		System.out.println(titleEscaped);
		NodeList nodeList = helper.findNodeList(response, "/root/result[@title = \"" + titleEscaped + "\"]");
		for (int index = 0; index < nodeList.getLength(); index++) {
			Node node = nodeList.item(index);
			System.out.println(node.getAttributes().getNamedItem("imdbID").getNodeValue());
		}
	}

	private static void testDetailsResponse(XmlHelper helper) {
		Document response = helper.loadXml("<root response=\"True\">\r\n"
				+ "<movie title=\"The Matrix\" year=\"1999\" rated=\"R\" released=\"31 Mar 1999\" runtime=\"136 min\" genre=\"Action, Sci-Fi\" director=\"Lana Wachowski, Lilly Wachowski\" writer=\"Lilly Wachowski, Lana Wachowski\" actors=\"Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss\" plot=\"Thomas A. Anderson is a man living two lives. By day he is an average computer programmer and by night a hacker known as Neo. Neo has always questioned his reality, but the truth is far beyond his imagination. Neo finds himself targeted by the police when he is contacted by Morpheus, a legendary computer hacker branded a terrorist by the government. As a rebel against the machines, Neo must confront the agents: super-powerful computer programs devoted to stopping Neo and the entire human rebellion.\" language=\"English\" country=\"United States, Australia\" awards=\"Won 4 Oscars. 42 wins 51 nominations total\" poster=\"https://m.media-amazon.com/images/M/MV5BNzQzOTk3OTAtNDQ0Zi00ZTVkLWI0MTEtMDllZjNkYzNjNTc4L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg\" metascore=\"73\" imdbRating=\"8.7\" imdbVotes=\"1,851,767\" imdbID=\"tt0133093\" type=\"movie\"/>\r\n"
				+ "</root>");
		// Find release date
		String dateStr = helper.findString(response, "/root/movie/@released");
		LocalDate date = DateUtils.toDateShortMonth(dateStr);
		System.out.println(DateUtils.toString(date));
		// Find director
		String director = helper.findString(response, "/root/movie/@director");
		System.out.println(director);
	}
}

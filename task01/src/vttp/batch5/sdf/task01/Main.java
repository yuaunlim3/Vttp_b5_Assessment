package vttp.batch5.sdf.task01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import vttp.batch5.sdf.task01.models.BikeEntry;

// Use this class as the entry point of your program

public class Main {

	public static final String FORMAT = "The <position> recorded number of cyclists was in <season>, on a <day> in the month of <month>. There were a total of <total> cyclists. The weather was <weather>. <day> was <holiday>.";

	public static void main(String[] args) {
		try {
			// Input file name and set reader
			String fileName = "day.csv";
			File file = new File(fileName);
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			List<String> categories = new ArrayList<String>();
			List<BikeEntry> datas = new ArrayList<BikeEntry>();
			List<BikeEntry> top5 = new ArrayList<BikeEntry>();

			categories = readFirstLine(br);
			datas = readDatas(br);

			top5 = getTop5(datas);

			for (int index = 0; index < 5; index++) {
				details(top5.get(index), index);
				System.out.println(" ");
				System.out.println(" ");
				System.out.println(" ");
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public static void details(BikeEntry infos, int index) {
		String position = "";
		String season = Utilities.toSeason(infos.getSeason());
		String day = Utilities.toWeekday(infos.getWeekday());
		String month = Utilities.toMonth(infos.getMonth());
		String holiday = "";
		int total = infos.getCasual() + infos.getRegistered();
		String weather = "";

		// Get position
		switch (index) {
			case 0:
				position = "highest";
				break;
			case 1:
				position = "second highest";
				break;

			case 2:
				position = "third highest";
				break;

			case 3:
				position = "fourth highest";
				break;

			case 4:
				position = "fifth highest";
				break;
			default:
				break;
		}

		// Get weather
		switch (infos.getWeather()) {
			case 1:
				weather = "Clear, Few clouds, Partly cloudy, Partly cloudy";
				break;

			case 2:
				weather = "Mist + Cloudy, Mist + Broken clouds, Mist + Few clouds, Mist";
				break;

			case 3:
				weather = " Light Snow, Light Rain + Thunderstorm + Scattered clouds, Light Rain + Scattered clouds";
				break;

			case 4:
				weather = "Heavy Rain + Ice Pallets + Thunderstorm + Mist, Snow + Fog";
				break;
			default:
				break;
		}

		// check holiday
		if (infos.isHoliday()) {
			holiday = "a holiday";
		} else {
			holiday = "not a holiday";
		}

		
		String[] sentence = FORMAT.split(" ");

		for (int idx = 0; idx < sentence.length; idx++) {
			String word = sentence[idx];

			String last = word.substring(word.length() - 1);
			if (!last.equals(">")) {
				word = word.substring(0, word.length() - 1);
			}
			switch (word) {
				case "<position>":
					if (last.equals(">")) {
						sentence[idx] = position + " (position)";
					} else {
						sentence[idx] = position + " (position)" + last;
					}
					break;

				case "<season>":
					if (last.equals(">")) {
						sentence[idx] = season + " (season)";
					} else {
						sentence[idx] = season + " (season)" + last;
					}
					break;

				case "<day>":
					if (last.equals(">")) {
						sentence[idx] = day + " (day)";
					} else {
						sentence[idx] = day + " (day)" + last;
					}
					break;

				case "<month>":
					if (last.equals(">")) {
						sentence[idx] = month + " (month)";
					} else {
						sentence[idx] = month + " (month)" + last;
					}
					break;

				case "<weather>":
					if (last.equals(">")) {
						sentence[idx] = weather + " (weather)";
					} else {
						sentence[idx] = weather + " (weather)" + last;
					}
					break;

				case "<total>":
					if (last.equals(">")) {
						sentence[idx] = total + " (total)";
					} else {
						sentence[idx] = total + " (total)" + last;
					}
					break;

				case "<holiday>":
					if (last.equals(">")) {
						sentence[idx] = holiday;
					} else {
						sentence[idx] = holiday + last;
					}
					break;

				default:
					break;
			}
		}

		for (String word : sentence) {
			System.err.print(word + " ");
		}

	}

	public static List<BikeEntry> getTop5(List<BikeEntry> datas) {

		List<BikeEntry> top5 = new ArrayList<>();
		datas.sort(Comparator.comparing(data -> data.getCasual() + data.getRegistered()));
		Collections.reverse(datas);

		for (int idx = 0; idx < 5; idx++) {
			// System.out.println(datas.get(idx).getCasual() +
			// datas.get(idx).getRegistered());
			top5.add(datas.get(idx));
		}

		return top5;
	}

	public static List<String> readFirstLine(BufferedReader br) throws IOException {
		String firstLine = br.readLine();
		List<String> categories = new ArrayList<String>();

		if (firstLine != null) {
			String[] infos = firstLine.split(",");
			for (String info : infos) {
				// System.out.printf("%s added to categories\n", info);
				categories.add(info);
			}
		}

		return categories;
	}

	public static List<BikeEntry> readDatas(BufferedReader br) throws IOException {
		List<BikeEntry> datas = new ArrayList<BikeEntry>();

		String line = "a";
		while (line != null) {
			line = br.readLine();
			if (line == null) {
				break;
			}
			String[] infos = line.split(",");
			BikeEntry bike = BikeEntry.toBikeEntry(infos);
			datas.add(bike);

		}
		return datas;

	}
}

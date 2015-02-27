package util;
import objects.Item;
import objects.ItemType;
import objects.Type;

public class Harvester {
	public Item getObjectItem(Type type) {
		switch(type)
		{
		case Tree:
			Item log = new Item();
			log.setCount(1);
			log.setName("Log");
			log.description ="This looks as though it might burn.";
			log.setType(ItemType.Log);
			log.setTexture(ItemType.getResourceTexture(log.getType()));
			return log;
		case Rock:
			Item rock = new Item();
			rock.setCount(1);
			rock.setName("Rock");
			rock.description ="This looks as though it will melt.";
			rock.setType(ItemType.Rock);
			rock.setTexture(ItemType.getResourceTexture(rock.getType()));
			return rock;
			default:
				return null;
		}
	}
}

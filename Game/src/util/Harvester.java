package util;

import objects.Item;
import objects.ItemType;
import objects.Type;

public class Harvester {
	public Item getObjectItem(Type type) {
		switch (type) {
		case Bush:
			Item log = new Item();
			log.setCount(1);
			log.setName("Log");
			log.description = "This is a Log.";
			log.setType(ItemType.Log);
			log.setTexture(ItemType.getResourceTexture(log.getType()));
			return log;
		case Maple:
			Item maplelog = new Item();
			maplelog.setCount(1);
			maplelog.setName("Maple Log");
			maplelog.description = "This is a Maple Log. (It look very red)";
			maplelog.setType(ItemType.Maple_Log);
			maplelog.setTexture(ItemType.getResourceTexture(maplelog.getType()));
			return maplelog;
		case Soul:
			Item soullog = new Item();
			soullog.setCount(1);
			soullog.setName("Soul Log");
			soullog.description = "This is a Soul Log. (You can sense the presence of lingering life)";
			soullog.setType(ItemType.Soul_Log);
			soullog.setTexture(ItemType.getResourceTexture(soullog.getType()));
			return soullog;
		case Rock:
			Item rock = new Item();
			rock.setCount(1);
			rock.setName("Rock");
			rock.description = "This looks as though it will melt.";
			rock.setType(ItemType.Rock);
			rock.setTexture(ItemType.getResourceTexture(rock.getType()));
			return rock;
		default:
			return null;
		}
	}
}

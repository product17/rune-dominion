package io.sandbox.rune_dominion.cache;

import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;

import dev.emi.trinkets.api.SlotReference;
import io.sandbox.rune_dominion.traits.Traits;

public class SlotCache {
  private static Map<String, UUID> CACHED_UUIDS = Maps.newHashMap();

  public static UUID getUuid(SlotReference ref, Traits trait) {
		String key = ref.inventory().getSlotType().getGroup() + "/" + ref.inventory().getSlotType().getName() + "/" + ref.index() + "/" + trait;
		CACHED_UUIDS.putIfAbsent(key, UUID.nameUUIDFromBytes(key.getBytes()));
		return CACHED_UUIDS.get(key);
	}
}

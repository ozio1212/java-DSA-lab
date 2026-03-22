package dsaa.lab02;

import java.util.Objects;

public class Link{
	public String ref;

	public Link(String ref) {
		this.ref=ref;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Link link = (Link) obj;
		return Objects.equals(ref, link.ref);
	}
	// in the future there will be more fields
}

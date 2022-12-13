input = <<INPUT
 ...
INPUT

def compare_lists(v1, v2)
  i = 0
  while true
    left = v1[i]
    right = v2[i]

    return nil if left == nil && right == nil
    return true if left == nil && right != nil
    return false if right == nil && left != nil

    right = [right] if left.is_a?(Array) && right.is_a?(Integer)
    left = [left] if right.is_a?(Array) && left.is_a?(Integer)

    if left.is_a?(Integer) && right.is_a?(Integer)
      return true if left < right
      return false if left > right
    elsif left.is_a?(Array) && right.is_a?(Array)
      c = compare_lists(left, right)
      return c unless c.nil?
    end
    i = i+1
  end
end

in_order_indices = []
input.split("\n\n").each_with_index do |pair, index|
  split_pair = pair.split("\n")
  left = eval(split_pair.first)
  right = eval(split_pair.last)
  if compare_lists(left, right)
    in_order_indices << index + 1
  end
end
puts("part1: #{in_order_indices.sum}")

extra_packet1 = [[2]]
extra_packet2 = [[6]]
packets = input.split("\n\n").flat_map do |pair|
  pair.split("\n").map { |p| eval(p) }
end
packets << extra_packet1
packets << extra_packet2

sorted_packets = packets.sort do |p1, p2|
  case compare_lists(p1, p2)
  when true
    -1
  when false
    1
  else
    0
  end
end

i1 = sorted_packets.index(extra_packet1) + 1
i2 = sorted_packets.index(extra_packet2) + 1
puts("part2: #{i1 * i2}")